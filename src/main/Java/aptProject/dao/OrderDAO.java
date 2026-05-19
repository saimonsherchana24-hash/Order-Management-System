package aptProject.dao;

import aptProject.dao.Interface.OrderDAOInterface;
import aptProject.model.Order;
import aptProject.model.OrderItem;
import aptProject.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDAO — Data Access Object for the { orders} and {order_items} tables.
 * <p>Handles placing new orders (as a single atomic transaction), fetching orders
 * by ID or user, updating order status, marking orders as paid, and providing
 * summary statistics for the admin dashboard.</p>
 */
public class OrderDAO implements OrderDAOInterface {

    // ------------------------------------------------------------------ place order

    /**
     * If any step fails the entire transaction is rolled back so the database is never left in a partial state.</p>
     * @param order the { Order} to persist (must contain at least one item)
     * @return the auto-generated order ID on success; {-1} on failure
     */
    @Override
    public int placeOrder(Order order) {
        // SQL to create the order header row (status and payment_status are set to defaults)
        String orderSql = "INSERT INTO orders (user_id, status, payment_status, total_price, special_note) " +
                          "VALUES (?, 'PENDING', 'UNPAID', ?, ?)";

        // SQL to insert a single line item linked to the order
        String itemSql  = "INSERT INTO order_items (order_id, menu_item_id, item_name, item_price, quantity) " +
                          "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // start transaction — all inserts succeed or all roll back

            // ── Step 1: Insert the order header and capture the generated ID ──
            int generatedId = -1;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getUserId());
                ps.setDouble(2, order.getTotalPrice());
                ps.setString(3, order.getSpecialNote());
                ps.executeUpdate();

                // Retrieve the auto-incremented primary key assigned to this order
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) generatedId = keys.getInt(1);
                }
            }

            // If no ID was generated the insert failed — roll back and abort
            if (generatedId == -1) {
                conn.rollback();
                return -1;
            }

            // ── Step 2: Insert each line item using a batch for efficiency ────
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                for (OrderItem item : order.getItems()) {
                    ps.setInt(1, generatedId); // link item to the newly created order

                    // Use NULL for menu_item_id when id is 0 (item not linked to menu)
                    if (item.getMenuItemId() > 0) {
                        ps.setInt(2, item.getMenuItemId());
                    } else {
                        ps.setNull(2, java.sql.Types.INTEGER); // no menu reference
                    }

                    ps.setString(3, item.getItemName());  // snapshot of name at order time
                    ps.setDouble(4, item.getItemPrice()); // snapshot of price at order time
                    ps.setInt(5, item.getQuantity());
                    ps.addBatch(); // queue this row for batch execution
                }
                ps.executeBatch(); // send all item inserts in one round-trip
            }

            conn.commit(); // all inserts succeeded — commit the transaction
            return generatedId;

        } catch (SQLException e) {
            e.printStackTrace();
            // Roll back any partial inserts if an error occurred
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return -1;
        } finally {
            // Always restore auto-commit and close the connection
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // ---------- get by id

    /**
     * Fetches a single order by its ID, including all associated line items.
     * @param orderId the primary key of the order
     * @return the matching { Order} with items populated, or {null} if not found
     */
    @Override
    public Order getOrderById(int orderId) {
        // Join with users table to include the customer's full name
        String sql = "SELECT o.id, o.user_id, o.status, o.payment_status, o.total_price, " +
                     "o.special_note, o.created_at, u.full_name AS customer_name " +
                     "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId); // bind the target order ID
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = mapOrder(rs);                          // build Order from row
                    order.setItems(getItemsForOrder(conn, orderId));     // attach line items
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // order not found
    }

    // ---------by user

    /**
     * Retrieves all orders placed by a specific customer, newest first.
     * @param userId the ID of the customer
     * @return list of { Order} objects (may be empty, never { null})
     */
    @Override
    public List<Order> getOrdersByUserId(int userId) {
        // Filter by user ID and sort by most recent first
        String sql = "SELECT o.id, o.user_id, o.status, o.payment_status, o.total_price, " +
                     "o.special_note, o.created_at, u.full_name AS customer_name " +
                     "FROM orders o JOIN users u ON o.user_id = u.id " +
                     "WHERE o.user_id = ? ORDER BY o.created_at DESC";
        return fetchOrders(sql, userId); // delegate to shared helper
    }

    // ---------- all orders

    /**
     * Retrieves every order in the system, newest first.
     * Used by the admin order management page.
     * @return list of all {Order} objects with items populated
     */
    @Override
    public List<Order> getAllOrders() {
        // No WHERE clause — fetch all orders sorted by most recent first
        String sql = "SELECT o.id, o.user_id, o.status, o.payment_status, o.total_price, " +
                     "o.special_note, o.created_at, u.full_name AS customer_name " +
                     "FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Iterate every row and build a fully populated Order object
            while (rs.next()) {
                Order order = mapOrder(rs);
                order.setItems(getItemsForOrder(conn, order.getId())); // attach line items
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // ----------update status

    /**
     * Updates the fulfilment status of an order.
     * @param orderId the ID of the order to update
     * @param status  the new status
     * @return {true} if the row was updated; { false} otherwise
     */
    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        // SQL to change only the status column for a specific order
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);  // new status value
            ps.setInt(2, orderId);    // target order

            return ps.executeUpdate() == 1; // 1 row updated = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------------------ mark paid

    /**
     * Sets an order's payment status to {PAID}.
     * @param orderId the ID of the order to mark as paid
     * @return {true} if the row was updated; { false} otherwise
     */
    @Override
    public boolean markAsPaid(int orderId) {
        // Hardcode 'PAID' — this method has a single, specific purpose
        String sql = "UPDATE orders SET payment_status = 'PAID' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId); // target order
            return ps.executeUpdate() == 1; // 1 row updated = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------- daily revenue (last 7 days)

    /**
     * Returns revenue per day for the last 7 days (including today).
     * <p>The result is a fixed-size array where index 0 represents 6 days ago
     * and index 6 represents today. Only orders with { payment_status = 'PAID'}
     * are included.</p>
     * @return {[7]} of daily revenue totals
     */
    @Override
    public double[] getDailyRevenue() {
        double[] revenue = new double[7]; // one slot per day, all initialised to 0.0

        // Aggregate paid order totals grouped by how many days ago they were placed
        String sql = "SELECT DATEDIFF(CURDATE(), DATE(created_at)) AS days_ago, " +
                     "       COALESCE(SUM(total_price), 0) AS daily_total " +
                     "FROM orders " +
                     "WHERE payment_status = 'PAID' " +
                     "  AND created_at >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
                     "GROUP BY days_ago";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int daysAgo = rs.getInt("days_ago");
                if (daysAgo >= 0 && daysAgo <= 6) {
                    // Map: index 6 = today (0 days ago), index 0 = 6 days ago
                    revenue[6 - daysAgo] = rs.getDouble("daily_total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }

    // -------------------- dashboard stats

    /**
     * Returns the total number of orders in the system.
     * @return count of all orders
     */
    @Override
    public int getTotalOrders() {
        return countQuery("SELECT COUNT(*) FROM orders");
    }

    /**
     * Returns the number of orders currently in { PENDING} status.
     * @return count of pending orders
     */
    @Override
    public int getPendingOrders() {
        return countQuery("SELECT COUNT(*) FROM orders WHERE status = 'PENDING'");
    }

    /**
     * Returns the number of orders that have reached { COMPLETED} status.
     * @return count of completed orders
     */
    @Override
    public int getCompletedOrders() {
        return countQuery("SELECT COUNT(*) FROM orders WHERE status = 'COMPLETED'");
    }

    /**
     * Returns the sum of all paid order totals.
     * @return total revenue; { 0.0} if no paid orders exist
     */
    @Override
    public double getTotalRevenue() {
        // COALESCE ensures 0 is returned instead of NULL when there are no paid orders
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM orders WHERE payment_status = 'PAID'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // -------------------- helpers

    /**
     * Shared helper that executes a parameterised SELECT and returns a list of orders.
     * @param sql    the SELECT query (must join users and return the expected columns)
     * @param userId the user ID to bind as parameter 1, or {@code null} for no parameter
     * @return list of {Order} objects with items populated
     */
    private List<Order> fetchOrders(String sql, Integer userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userId != null) ps.setInt(1, userId); // bind user filter if provided
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapOrder(rs);
                    order.setItems(getItemsForOrder(conn, order.getId())); // attach line items
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Fetches all line items belonging to a specific order.
     * @param conn    an open database connection (reused from the caller)
     * @param orderId the order whose items should be fetched
     * @return list of { OrderItem} objects
     * @throws SQLException if the query fails
     */
    private List<OrderItem> getItemsForOrder(Connection conn, int orderId) throws SQLException {
        // Select all columns needed to reconstruct each OrderItem
        String sql = "SELECT id, order_id, menu_item_id, item_name, item_price, quantity " +
                     "FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId); // filter items by the parent order ID
            try (ResultSet rs = ps.executeQuery()) {
                // Build an OrderItem for every row returned
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setItemName(rs.getString("item_name"));   // name snapshot
                    item.setItemPrice(rs.getDouble("item_price")); // price snapshot
                    item.setQuantity(rs.getInt("quantity"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    /**
     * Maps the current row of a {ResultSet} to an { Order} object.
     * @param rs an open ResultSet positioned on a valid row
     * @return a populated {Order} (items list not yet attached)
     * @throws SQLException if any column cannot be read
     */
    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setStatus(rs.getString("status"));
        order.setPaymentStatus(rs.getString("payment_status"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setSpecialNote(rs.getString("special_note"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setCustomerName(rs.getString("customer_name")); // joined from users table
        return order;
    }

    /**
     * Executes a simple {SELECT COUNT(*)} query and returns the integer result.
     * @param sql a COUNT query with no parameters
     * @return the count value; {0} on error
     */
    private int countQuery(String sql) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1); // first column holds the count
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

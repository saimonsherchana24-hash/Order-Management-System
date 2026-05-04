package aptProject.dao;

import aptProject.dao.Interface.OrderDAOInterface;
import aptProject.model.Order;
import aptProject.model.OrderItem;
import aptProject.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements OrderDAOInterface {

    // ------------------------------------------------------------------ place order
    @Override
    public int placeOrder(Order order) {
        String orderSql = "INSERT INTO orders (user_id, status, payment_status, total_price, special_note) " +
                          "VALUES (?, 'PENDING', 'UNPAID', ?, ?)";
        String itemSql  = "INSERT INTO order_items (order_id, menu_item_id, item_name, item_price, quantity) " +
                          "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Insert order header
            int generatedId = -1;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getUserId());
                ps.setDouble(2, order.getTotalPrice());
                ps.setString(3, order.getSpecialNote());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) generatedId = keys.getInt(1);
                }
            }

            if (generatedId == -1) {
                conn.rollback();
                return -1;
            }

            // 2. Insert each order item
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                for (OrderItem item : order.getItems()) {
                    ps.setInt(1, generatedId);
                    ps.setInt(2, item.getMenuItemId());
                    ps.setString(3, item.getItemName());
                    ps.setDouble(4, item.getItemPrice());
                    ps.setInt(5, item.getQuantity());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return generatedId;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return -1;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // ------------------------------------------------------------------ get by id
    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT o.id, o.user_id, o.status, o.payment_status, o.total_price, " +
                     "o.special_note, o.created_at, u.full_name AS customer_name " +
                     "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = mapOrder(rs);
                    order.setItems(getItemsForOrder(conn, orderId));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ by user
    @Override
    public List<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT o.id, o.user_id, o.status, o.payment_status, o.total_price, " +
                     "o.special_note, o.created_at, u.full_name AS customer_name " +
                     "FROM orders o JOIN users u ON o.user_id = u.id " +
                     "WHERE o.user_id = ? ORDER BY o.created_at DESC";
        return fetchOrders(sql, userId);
    }

    // ------------------------------------------------------------------ all orders
    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT o.id, o.user_id, o.status, o.payment_status, o.total_price, " +
                     "o.special_note, o.created_at, u.full_name AS customer_name " +
                     "FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = mapOrder(rs);
                order.setItems(getItemsForOrder(conn, order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // ------------------------------------------------------------------ update status
    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------------------ mark paid
    @Override
    public boolean markAsPaid(int orderId) {
        String sql = "UPDATE orders SET payment_status = 'PAID' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------------------ dashboard stats
    @Override
    public int getTotalOrders() {
        return countQuery("SELECT COUNT(*) FROM orders");
    }

    @Override
    public int getPendingOrders() {
        return countQuery("SELECT COUNT(*) FROM orders WHERE status = 'PENDING'");
    }

    @Override
    public int getCompletedOrders() {
        return countQuery("SELECT COUNT(*) FROM orders WHERE status = 'COMPLETED'");
    }

    @Override
    public double getTotalRevenue() {
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

    // ------------------------------------------------------------------ helpers
    private List<Order> fetchOrders(String sql, Integer userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (userId != null) ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapOrder(rs);
                    order.setItems(getItemsForOrder(conn, order.getId()));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<OrderItem> getItemsForOrder(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT id, order_id, menu_item_id, item_name, item_price, quantity " +
                     "FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setItemPrice(rs.getDouble("item_price"));
                    item.setQuantity(rs.getInt("quantity"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setStatus(rs.getString("status"));
        order.setPaymentStatus(rs.getString("payment_status"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setSpecialNote(rs.getString("special_note"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setCustomerName(rs.getString("customer_name"));
        return order;
    }

    private int countQuery(String sql) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

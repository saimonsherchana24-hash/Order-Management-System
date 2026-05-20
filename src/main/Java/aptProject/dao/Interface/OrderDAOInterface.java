package aptProject.dao.Interface;

import aptProject.model.Order;
import java.util.List;
import java.util.ArrayList;
import aptProject.model.WeeklyRevenue;

/**
 * OrderDAOInterface — contract for all order-related database operations.
 * <p>Defines the full set of operations needed to create, retrieve, and
 * update orders, as well as fetch summary statistics for the admin dashboard.
 * Implemented by {@code OrderDAO}.</p>
 */
public interface OrderDAOInterface {

    /**
     * Persists a new order (header + line items) in a single transaction.
     * @param order the {@link Order} object containing user ID, items, total, and note
     * @return the auto-generated order ID on success; {@code -1} on failure
     */
    int placeOrder(Order order);

    /**
     * Retrieves a single order by its primary key, including all line items.
     * @param orderId the ID of the order to fetch
     * @return the matching {@link Order}, or {@code null} if not found
     */
    Order getOrderById(int orderId);

    /**
     * Retrieves all orders placed by a specific user, newest first.
     * @param userId the ID of the customer
     * @return a list of {@link Order} objects (may be empty, never {@code null})
     */
    List<Order> getOrdersByUserId(int userId);

    /**
     * Retrieves every order in the system, newest first.
     * Used by the admin order management page.
     * @return a list of all {@link Order} objects (may be empty, never {@code null})
     */
    List<Order> getAllOrders();

    /**
     * Updates the fulfilment status of an order
     * @param status  the new status string (PENDING, COMPLETED)
     * @return { true} if the row was updated; { false} otherwise
     */
    boolean updateOrderStatus(int orderId, String status);

    /**
     * Sets an order's payment status to {@code PAID}.
     * @param orderId the ID of the order to mark as paid
     * @return {@code true} if the row was updated; {false} otherwise
     */
    boolean markAsPaid(int orderId);

    /**
     * Returns the total number of orders in the system.
     * @return count of all orders
     */
    int getTotalOrders();

    /**
     * Returns the number of orders currently in { PENDING} status.
     *
     * @return count of pending orders
     */
    int getPendingOrders();

    /**
     * Returns the number of orders that have reached {COMPLETED} status.
     *@return count of completed orders
     */
    int getCompletedOrders();

    // Returns the sum of {@code total_price} for all orders with payment status {@code PAID}.
    double getTotalRevenue();

    ArrayList<WeeklyRevenue> getWeeklyRevenue();
}

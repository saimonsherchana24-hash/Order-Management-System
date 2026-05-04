package aptProject.dao.Interface;

import aptProject.model.Order;
import java.util.List;

public interface OrderDAOInterface {
    /** Place a new order; returns the generated order ID, or -1 on failure */
    int placeOrder(Order order);

    /** Get a single order with its items */
    Order getOrderById(int orderId);

    /** All orders for a specific user */
    List<Order> getOrdersByUserId(int userId);

    /** All orders (admin view) */
    List<Order> getAllOrders();

    /** Update order status: PENDING → ACCEPTED → PREPARING → READY → COMPLETED / REJECTED */
    boolean updateOrderStatus(int orderId, String status);

    /** Mark an order bill as PAID */
    boolean markAsPaid(int orderId);

    // --- Dashboard stats ---
    int getTotalOrders();
    int getPendingOrders();
    int getCompletedOrders();
    double getTotalRevenue();
}

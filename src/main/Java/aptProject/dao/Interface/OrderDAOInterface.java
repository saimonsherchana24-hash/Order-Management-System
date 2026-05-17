package aptProject.dao.Interface;

import aptProject.model.Order;
import java.util.List;

public interface OrderDAOInterface {

    int placeOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getOrdersByUserId(int userId);

    List<Order> getAllOrders();

    boolean updateOrderStatus(int orderId, String status);

    boolean markAsPaid(int orderId);

    int getTotalOrders();

    int getPendingOrders();

    int getCompletedOrders();

    double getTotalRevenue();
}

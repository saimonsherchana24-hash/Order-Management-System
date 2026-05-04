package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.model.Order;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "TrackingServlet", urlPatterns = {"/tracking"})
public class TrackingServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        String myOrders     = request.getParameter("myOrders");

        if (orderIdParam != null && !orderIdParam.isBlank()) {
            // Search by order ID
            int orderId = Integer.parseInt(orderIdParam.trim());
            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                request.setAttribute("error", "No order found with ID: " + orderId);
            } else {
                request.setAttribute("order", order);
            }

        } else if (myOrders != null) {
            // Show all orders for this user
            List<Order> orders = orderDAO.getOrdersByUserId(SessionUtil.getUserId(request));
            request.setAttribute("orders", orders);
        }

        request.getRequestDispatcher("/page/Tracking.jsp").forward(request, response);
    }
}

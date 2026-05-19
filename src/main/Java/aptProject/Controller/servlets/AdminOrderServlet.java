package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * AdminOrderServlet — allows admins to view all orders and update their status.
 * Only accessible to users with the ADMIN role; others are redirected to login.
 */
@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/orders", "/admin/orders/updateStatus"})
public class AdminOrderServlet extends HttpServlet {

    // DAO used to fetch and update order records in the database
    private final OrderDAO orderDAO = new OrderDAO();

    // GET — load all orders from the database and display them in the admin panel.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Authentication handled by AuthFilter

        // Fetch every order from the database and pass the list to the JSP
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/page/AdminOrder.jsp").forward(request, response);
    }

    /**
     * POST — update the status of a single order (e.g. Pending → Preparing → Delivered).
     * Redirects back to the order list after the update (PRG pattern).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Authentication handled by AuthFilter

        // Read the target order ID and the new status value from the form
        int    orderId   = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("status");

        // Persist the new status to the database
        orderDAO.updateOrderStatus(orderId, newStatus);

        // Redirect back to the order list after the update to prevent double-submit
        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }
}

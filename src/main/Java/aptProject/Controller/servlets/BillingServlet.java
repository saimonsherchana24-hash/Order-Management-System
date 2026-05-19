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

/**
 * BillingServlet — provides the admin with a billing overview and the ability
 * to mark individual orders as paid.
 * Only accessible to users with the ADMIN role; others are redirected to login.
 */
@WebServlet(name = "BillingServlet", urlPatterns = {"/admin/billing", "/admin/billing/markPaid"})
public class BillingServlet extends HttpServlet {

    // DAO used to fetch order data and update payment status in the database
    private final OrderDAO orderDAO = new OrderDAO();
      //GET — load all orders and the total revenue figure, then display the billing page.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Block non-admin users from accessing the billing page
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Fetch every order to display in the billing table
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders",       orders);

        // Fetch the cumulative revenue figure for the summary card
        request.setAttribute("totalRevenue", orderDAO.getTotalRevenue());

        // Forward to the billing JSP with the data attached
        request.getRequestDispatcher("/WEB-INF/page/AdminBilling.jsp").forward(request, response);
    }
     // POST — mark the specified order as paid and redirect back to the billing page.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Block non-admin users from submitting payment updates
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Read the order ID from the form submission
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Update the order's payment status to "paid" in the database
        orderDAO.markAsPaid(orderId);

        // PRG pattern: redirect back to billing page to prevent double-submit on refresh
        response.sendRedirect(request.getContextPath() + "/admin/billing");
    }
}

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


@WebServlet(name = "BillingServlet", urlPatterns = {"/admin/billing", "/admin/billing/markPaid"})
public class BillingServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders",       orders);
        request.setAttribute("totalRevenue", orderDAO.getTotalRevenue());

        request.getRequestDispatcher("/page/AdminBilling.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        orderDAO.markAsPaid(orderId);

        // Redirect back to billing page after marking paid
        response.sendRedirect(request.getContextPath() + "/admin/billing");
    }
}

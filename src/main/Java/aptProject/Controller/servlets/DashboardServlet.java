package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "DashboardServlet", urlPatterns = {"/admin/dashboard"})
public class DashboardServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        // Pass live stats to the JSP
        request.setAttribute("totalOrders",     orderDAO.getTotalOrders());
        request.setAttribute("pendingOrders",   orderDAO.getPendingOrders());
        request.setAttribute("completedOrders", orderDAO.getCompletedOrders());
        request.setAttribute("totalRevenue",    orderDAO.getTotalRevenue());

        request.getRequestDispatcher("/page/AdminDashboard.jsp").forward(request, response);
    }
}

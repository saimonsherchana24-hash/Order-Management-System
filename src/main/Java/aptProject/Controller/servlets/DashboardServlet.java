package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.utilities.SessionUtil;
import aptProject.model.WeeklyRevenue;
import java.util.ArrayList;

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


        ArrayList<WeeklyRevenue> weeklyRevenue = orderDAO.getWeeklyRevenue();

ArrayList<String> dates = new ArrayList<>();
ArrayList<Double> revenues = new ArrayList<>();

for (WeeklyRevenue wr : weeklyRevenue) {
    dates.add(wr.getDate());
    revenues.add(wr.getRevenue());
}

request.setAttribute("dates", dates);
request.setAttribute("revenues", revenues);


        request.getRequestDispatcher("/page/AdminDashboard.jsp").forward(request, response);
    }
}

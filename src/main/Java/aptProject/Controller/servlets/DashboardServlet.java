package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/admin/dashboard"})
public class DashboardServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ── Summary stats ──
        request.setAttribute("totalOrders",     orderDAO.getTotalOrders());
        request.setAttribute("pendingOrders",   orderDAO.getPendingOrders());
        request.setAttribute("completedOrders", orderDAO.getCompletedOrders());
        request.setAttribute("totalRevenue",    orderDAO.getTotalRevenue());

        // ── Daily revenue for last 7 days ──
        double[] dailyRevenue = orderDAO.getDailyRevenue();
        request.setAttribute("dailyRevenue", dailyRevenue);

        // ── Day labels: Mon, Tue ... for last 7 days ──
        String[] dayLabels = new String[7];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            dayLabels[i] = today.minusDays(6 - i)
                               .getDayOfWeek()
                               .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        }
        request.setAttribute("dayLabels", dayLabels);

        // ── Best day label (highest revenue) ──
        int bestIdx = 0;
        for (int i = 1; i < 7; i++) {
            if (dailyRevenue[i] > dailyRevenue[bestIdx]) bestIdx = i;
        }
        request.setAttribute("bestDay", dayLabels[bestIdx]);
        request.setAttribute("bestDayRevenue", dailyRevenue[bestIdx]);

        request.getRequestDispatcher("/WEB-INF/page/AdminDashboard.jsp").forward(request, response);
    }
}

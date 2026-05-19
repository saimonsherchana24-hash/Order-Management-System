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

 // GET /admin/dashboard  load summary stats and revenue chart data, then forward to AdminDashboard.jsp

@WebServlet(name = "DashboardServlet", urlPatterns = {"/admin/dashboard"})
public class DashboardServlet extends HttpServlet {

    // DAO used to query order statistics and revenue figures from the database
    private final OrderDAO orderDAO = new OrderDAO();


    //  GET — gather all dashboard data and forward to the admin dashboard JSP.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Block non-admin users from accessing the dashboard
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ── Summary stats — top-level KPI cards on the dashboard ──
        request.setAttribute("totalOrders",     orderDAO.getTotalOrders());     // all-time order count
        request.setAttribute("pendingOrders",   orderDAO.getPendingOrders());   // orders not yet completed
        request.setAttribute("completedOrders", orderDAO.getCompletedOrders()); // fulfilled orders
        request.setAttribute("totalRevenue",    orderDAO.getTotalRevenue());    // all-time revenue sum

        // ── Daily revenue array for the last 7 days (used by the bar chart) ──
        double[] dailyRevenue = orderDAO.getDailyRevenue();
        request.setAttribute("dailyRevenue", dailyRevenue);

        // ── Build human-readable day labels (e.g. "Mon", "Tue") for the chart x-axis ──
        String[] dayLabels = new String[7];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            // Work backwards from today: index 0 = 6 days ago, index 6 = today
            dayLabels[i] = today.minusDays(6 - i)
                               .getDayOfWeek()
                               .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        }
        request.setAttribute("dayLabels", dayLabels);

        // ── Find the day with the highest revenue to highlight on the dashboard ──
        int bestIdx = 0;
        for (int i = 1; i < 7; i++) {
            // Track the index of the highest daily revenue value
            if (dailyRevenue[i] > dailyRevenue[bestIdx]) bestIdx = i;
        }
        request.setAttribute("bestDay",        dayLabels[bestIdx]);      // label of the best day
        request.setAttribute("bestDayRevenue", dailyRevenue[bestIdx]);   // revenue on that day

        // Forward all attributes to the dashboard JSP for rendering
        request.getRequestDispatcher("/WEB-INF/page/AdminDashboard.jsp").forward(request, response);
    }
}

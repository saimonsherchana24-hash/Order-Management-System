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
 * TrackingServlet — lets a logged-in user track their orders.
 *   GET /tracking → display the Tracking.jsp page
 *parameters:
 *   ?orderId=123   → look up and display a single order by its ID
 *   ?myOrders      → list all orders belonging to the current user
 *   (no params)    → show the tracking page with no results pre-loaded
 */
@WebServlet(name = "TrackingServlet", urlPatterns = {"/tracking"})
public class TrackingServlet extends HttpServlet {

    // DAO used to query order records from the database
    private final OrderDAO orderDAO = new OrderDAO();

     // GET — determine the query mode from the request parameters and load the appropriate order data before forwarding to the tracking JSP.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redirect unauthenticated users to the login page
        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Read the optional query parameters to determine what to display
        String orderIdParam = request.getParameter("orderId");
        String myOrders     = request.getParameter("myOrders");

        // Mode 1: a specific order ID was provided — look it up and show its status
        if (orderIdParam != null && !orderIdParam.isBlank()) {
            int orderId = Integer.parseInt(orderIdParam.trim()); // parse the order ID from the URL
            Order order = orderDAO.getOrderById(orderId);        // fetch the order from the database

            // If no matching order was found, set an error message for the JSP
            if (order == null) {
                request.setAttribute("error", "No order found with ID: " + orderId);
            } else {
                // Pass the found order to the JSP for display
                request.setAttribute("order", order);
            }

        } else if (myOrders != null) {
            // Mode 2: "myOrders" parameter present — fetch all orders for the current user
            List<Order> orders = orderDAO.getOrdersByUserId(SessionUtil.getUserId(request));
            request.setAttribute("orders", orders); // pass the full list to the JSP
        }
        // Mode 3: no parameters — forward to the tracking page with an empty search form

        // Forward to the tracking JSP with whatever data was loaded above
        request.getRequestDispatcher("/WEB-INF/page/Tracking.jsp").forward(request, response);
    }
}

package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.model.Order;
import aptProject.model.OrderItem;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "OrderServlet", urlPatterns = {"/order/checkout", "/order/place", "/order/confirm"})
public class OrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        if ("/order/checkout".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/page/Checkout.jsp").forward(request, response);

        } else if ("/order/confirm".equals(path)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDAO.getOrderById(orderId);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/page/confirmation.jsp").forward(request, response);
        }
    }

    /**
     * POST /order/place
     * Reads cart items sent from Checkout.jsp form, saves to DB.
     *
     * Form fields expected:
     *   totalPrice, specialNote, itemCount
     *   itemId_0, itemName_0, itemPrice_0, itemQty_0  (for each item)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int    userId    = SessionUtil.getUserId(request);
        double total     = Double.parseDouble(request.getParameter("totalPrice"));
        String note      = request.getParameter("specialNote");
        int    itemCount = Integer.parseInt(request.getParameter("itemCount"));

        // Build list of ordered items
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            String idParam  = request.getParameter("itemId_"    + i);
            String itemName = request.getParameter("itemName_"  + i);
            double itemPrice = Double.parseDouble(request.getParameter("itemPrice_" + i));
            int    qty       = Integer.parseInt(request.getParameter("itemQty_"   + i));

            // Parse menu item id — 0 or blank means unknown (cart item with no DB id)
            int menuItemId = 0;
            if (idParam != null && !idParam.isBlank() && !idParam.equals("0")) {
                try { menuItemId = Integer.parseInt(idParam); } catch (NumberFormatException ignored) {}
            }

            items.add(new OrderItem(menuItemId, itemName, itemPrice, qty));
        }
        // Build and save the order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setSpecialNote(note);
        order.setItems(items);

        int newOrderId = orderDAO.placeOrder(order);

        if (newOrderId != -1) {
            // Redirect to confirmation (prevents double-submit on refresh)
            response.sendRedirect(request.getContextPath() + "/order/confirm?orderId=" + newOrderId);
        } else {
            request.setAttribute("error", "Failed to place order. Please try again.");
            request.getRequestDispatcher("/WEB-INF/page/Checkout.jsp").forward(request, response);
        }
    }
}

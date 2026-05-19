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

/**
 * OrderServlet — manages the checkout flow from cart to confirmed order.
 *   GET  /order/checkout → display the checkout page (Checkout.jsp)
 *   GET  /order/confirm  → display the order confirmation page for a placed order
 *   POST /order/place    → receive cart data from the checkout form, save the order,and redirect to the confirmation page
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order/checkout", "/order/place", "/order/confirm"})
public class OrderServlet extends HttpServlet {

    // DAO used to save new orders and retrieve existing ones from the database
    private final OrderDAO orderDAO = new OrderDAO();

     // GET — show either the checkout page or the order confirmation page.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Authentication handled by AuthFilter

        String path = request.getServletPath();

        // Show the checkout form where the user reviews their cart before placing the order
        if ("/order/checkout".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/page/Checkout.jsp").forward(request, response);

        } else if ("/order/confirm".equals(path)) {
            // Load the just-placed order by its ID and display the confirmation page
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDAO.getOrderById(orderId); // fetch full order details from DB
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/page/confirmation.jsp").forward(request, response);
        }
    }

     // POST /order/place — read cart items from the form, build an Order object,

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Authentication handled by AuthFilter

        // Read the order-level fields from the form
        int    userId    = SessionUtil.getUserId(request);                          // ID of the logged-in user
        double total     = Double.parseDouble(request.getParameter("totalPrice"));  // total cost of the order
        String note      = request.getParameter("specialNote");                     // optional delivery note
        int    itemCount = Integer.parseInt(request.getParameter("itemCount"));     // number of line items

        // Build the list of ordered items by reading each indexed set of fields
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            String idParam   = request.getParameter("itemId_"    + i);
            String itemName  = request.getParameter("itemName_"  + i);
            double itemPrice = Double.parseDouble(request.getParameter("itemPrice_" + i));
            int    qty       = Integer.parseInt(request.getParameter("itemQty_"   + i));

            // Parse the menu item ID; default to 0 if it is missing or not a valid number
            int menuItemId = 0;
            if (idParam != null && !idParam.isBlank() && !idParam.equals("0")) {
                try { menuItemId = Integer.parseInt(idParam); } catch (NumberFormatException ignored) {}
            }

            // Add this line item to the list
            items.add(new OrderItem(menuItemId, itemName, itemPrice, qty));
        }

        // Assemble the complete Order object with all its line items
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setSpecialNote(note);
        order.setItems(items);

        // Persist the order to the database and get back the generated order ID
        int newOrderId = orderDAO.placeOrder(order);

        if (newOrderId != -1) {
            //  redirect to the confirmation GET to prevent double-submit on refresh
            response.sendRedirect(request.getContextPath() + "/order/confirm?orderId=" + newOrderId);
        } else {
            // Order save failed — show an error and let the user try again
            request.setAttribute("error", "Failed to place order. Please try again.");
            request.getRequestDispatcher("/WEB-INF/page/Checkout.jsp").forward(request, response);
        }
    }
}

package aptProject.Controller.servlets;

import aptProject.dao.MenuItemDAO;
import aptProject.model.MenuItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * MenuServlet — displays the public-facing menu page for customers.
 *   GET /menu → load menu items (optionally filtered by category) and forward to menu.jsp
 */
@WebServlet(name = "MenuServlet", urlPatterns = {"/menu"})
public class MenuServlet extends HttpServlet {

    // DAO used to retrieve menu items from the database
    private final MenuItemDAO menuDAO = new MenuItemDAO();

    //GET — load the appropriate menu items and forward to the menu JSP.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read the optional category filter from the query string (e.g. ?category=pizza)
        String category = request.getParameter("category");

        List<MenuItem> items;
        // If a category was specified, fetch only items in that category
        if (category != null && !category.isBlank()) {
            items = menuDAO.getItemsByCategory(category.trim());
        } else {
            // No filter — return every item on the menu
            items = menuDAO.getAllItems();
        }

        // Pass the item list to the JSP for rendering
        request.setAttribute("menuItems", items);

        // Pass the active category so the JSP can highlight the correct filter tab
        request.setAttribute("activeCategory", category != null ? category : "all");

        // Forward to the customer-facing menu JSP
        request.getRequestDispatcher("/WEB-INF/page/menu.jsp").forward(request, response);
    }
}

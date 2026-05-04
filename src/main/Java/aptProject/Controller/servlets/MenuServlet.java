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


@WebServlet(name = "MenuServlet", urlPatterns = {"/menu"})
public class MenuServlet extends HttpServlet {

    private final MenuItemDAO menuDAO = new MenuItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");

        List<MenuItem> items;
        if (category != null && !category.isBlank()) {
            items = menuDAO.getItemsByCategory(category.trim());
        } else {
            items = menuDAO.getAllItems();
        }

        request.setAttribute("menuItems", items);
        request.setAttribute("activeCategory", category != null ? category : "all");
        request.getRequestDispatcher("/page/menu.jsp").forward(request, response);
    }
}

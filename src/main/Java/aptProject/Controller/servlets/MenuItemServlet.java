package aptProject.Controller.servlets;

import aptProject.dao.MenuItemDAO;
import aptProject.model.MenuItem;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "MenuItemServlet",
        urlPatterns = {"/admin/menu", "/admin/menu/add", "/admin/menu/update", "/admin/menu/delete"})
public class MenuItemServlet extends HttpServlet {

    private final MenuItemDAO menuDAO = new MenuItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        request.setAttribute("menuItems", menuDAO.getAllItems());
        request.getRequestDispatcher("/page/AdminMenu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        String path = request.getServletPath();

        if ("/admin/menu/add".equals(path)) {
            // Read form fields and add new item
            String name        = request.getParameter("itemName");
            String category    = request.getParameter("category");
            double price       = Double.parseDouble(request.getParameter("price"));
            String description = request.getParameter("description");
            String imageUrl    = request.getParameter("imageUrl");

            MenuItem item = new MenuItem(name, category, price, description, imageUrl);
            menuDAO.addItem(item);

        } else if ("/admin/menu/update".equals(path)) {
            // Read form fields and update existing item
            int    id          = Integer.parseInt(request.getParameter("itemId"));
            String name        = request.getParameter("itemName");
            String category    = request.getParameter("category");
            double price       = Double.parseDouble(request.getParameter("price"));
            String description = request.getParameter("description");
            String imageUrl    = request.getParameter("imageUrl");

            MenuItem item = new MenuItem(name, category, price, description, imageUrl);
            item.setId(id);
            menuDAO.updateItem(item);

        } else if ("/admin/menu/delete".equals(path)) {
            int id = Integer.parseInt(request.getParameter("itemId"));
            menuDAO.deleteItem(id);
        }

        // Always go back to the menu list after any action
        response.sendRedirect(request.getContextPath() + "/admin/menu");
    }
}

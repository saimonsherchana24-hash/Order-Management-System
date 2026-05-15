package aptProject.Controller.servlets;

import aptProject.dao.MenuItemDAO;
import aptProject.model.MenuItem;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * MenuItemServlet - admin adds, edits, and deletes menu items.
 *
 * GET  /admin/menu          → show all menu items in AdminMenu.jsp
 * POST /admin/menu/add      → add a new item (with image upload from device)
 * POST /admin/menu/update   → edit an existing item
 * POST /admin/menu/delete   → delete an item
 */
@WebServlet(name = "MenuItemServlet",
        urlPatterns = {"/admin/menu", "/admin/menu/add", "/admin/menu/update", "/admin/menu/delete"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB — keep in memory below this
        maxFileSize       = 5 * 1024 * 1024,  // 5 MB max per file
        maxRequestSize    = 10 * 1024 * 1024  // 10 MB max total request
)
public class MenuItemServlet extends HttpServlet {

    private final MenuItemDAO menuDAO = new MenuItemDAO();

    // ── GET: load all items ──────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        request.setAttribute("menuItems", menuDAO.getAllItems());
        request.getRequestDispatcher("/page/AdminMenu.jsp").forward(request, response);
    }

    // ── POST: route by action ────────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        String path = request.getServletPath();

        switch (path) {
            case "/admin/menu/add":    addItem(request, response);    break;
            case "/admin/menu/update": updateItem(request, response); break;
            case "/admin/menu/delete": deleteItem(request, response); break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/menu");
        }
    }

    // ── add with image upload ────────────────────────────────────────────────
    private void addItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String name        = request.getParameter("itemName");
        String category    = request.getParameter("category");
        String priceParam  = request.getParameter("price");
        String description = request.getParameter("description");

        if (name == null || name.isBlank() || category == null || priceParam == null) {
            request.setAttribute("error", "Please fill all required fields.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/page/AdminMenu.jsp").forward(request, response);
            return;
        }

        try {
            double price = Double.parseDouble(priceParam.trim());

            // Handle image upload
            String imageUrl = saveUploadedImage(request, "imageFile");

            MenuItem item = new MenuItem(name.trim(), category.trim().toLowerCase(),
                                         price, description, imageUrl);
            menuDAO.addItem(item);
            response.sendRedirect(request.getContextPath() + "/admin/menu?success=added");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid price value.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/page/AdminMenu.jsp").forward(request, response);
        }
    }

    // ── update ───────────────────────────────────────────────────────────────
    private void updateItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idParam        = request.getParameter("itemId");
        String name           = request.getParameter("itemName");
        String category       = request.getParameter("category");
        String priceParam     = request.getParameter("price");
        String description    = request.getParameter("description");
        String existingImage  = request.getParameter("existingImage"); // keep old image if no new upload

        if (idParam == null || name == null || name.isBlank() || priceParam == null) {
            request.setAttribute("error", "Please fill all required fields.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/page/AdminMenu.jsp").forward(request, response);
            return;
        }

        try {
            int    id    = Integer.parseInt(idParam.trim());
            double price = Double.parseDouble(priceParam.trim());

            // Check if a new image was uploaded
            String imageUrl = existingImage; // default: keep existing
            Part filePart = request.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                String uploaded = saveUploadedImage(request, "imageFile");
                if (uploaded != null) imageUrl = uploaded;
            }

            MenuItem item = new MenuItem(name.trim(), category.trim().toLowerCase(),
                                         price, description, imageUrl);
            item.setId(id);
            menuDAO.updateItem(item);
            response.sendRedirect(request.getContextPath() + "/admin/menu?success=updated");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID or price.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/page/AdminMenu.jsp").forward(request, response);
        }
    }

    // ── delete ───────────────────────────────────────────────────────────────
    private void deleteItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idParam = request.getParameter("itemId");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/menu?error=missingId");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim());
            menuDAO.deleteItem(id);
            response.sendRedirect(request.getContextPath() + "/admin/menu?success=deleted");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/menu?error=invalidId");
        }
    }

    // ── save uploaded image to /Resource folder ──────────────────────────────
    private String saveUploadedImage(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {

        Part filePart = request.getPart(fieldName);

        if (filePart == null || filePart.getSize() == 0) {
            return "../Resource/default.jpg"; // fallback if no image uploaded
        }

        // Get original filename
        String submittedFileName = filePart.getSubmittedFileName();
        if (submittedFileName == null || submittedFileName.isBlank()) {
            return "../Resource/default.jpg";
        }

        // Sanitize filename — keep only safe characters
        String fileName = System.currentTimeMillis() + "_"
                + submittedFileName.replaceAll("[^a-zA-Z0-9._-]", "_");

        // Save to webapp/Resource folder
        String uploadDir = getServletContext().getRealPath("/Resource");
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        // Return relative path used in JSP/menu
        return "../Resource/" + fileName;
    }

    // ── guard ────────────────────────────────────────────────────────────────
    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}

package aptProject.Controller.servlets;

import aptProject.dao.MenuItemDAO;
import aptProject.model.MenuItem;
import aptProject.utilities.SessionUtil;
import aptProject.utilities.UploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

/**
 * MenuItemServlet — admin CRUD operations for menu items.
 *   GET  /admin/menu          → list all menu items in AdminMenu.jsp
 *   POST /admin/menu/add      → add a new menu item (with optional image upload)
 *   POST /admin/menu/update   → edit an existing menu item
 *   POST /admin/menu/delete   → remove a menu item by ID
 */
@WebServlet(name = "MenuItemServlet",
        urlPatterns = {"/admin/menu", "/admin/menu/add", "/admin/menu/update", "/admin/menu/delete"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // keep files up to 1 MB in memory before writing to disk
        maxFileSize       = 5 * 1024 * 1024,  // reject individual files larger than 5 MB
        maxRequestSize    = 10 * 1024 * 1024  // reject entire requests larger than 10 MB
)
public class MenuItemServlet extends HttpServlet {

    // DAO used to read and write menu item records in the database
    private final MenuItemDAO menuDAO = new MenuItemDAO();

    // ── GET: load all items and display the admin menu page ──────────────────

    //GET — fetch all menu items and forward to the admin menu JSP.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reject non-admin users before loading any data
        if (!isAdmin(request, response)) return;

        // Fetch all menu items and pass them to the JSP for rendering
        request.setAttribute("menuItems", menuDAO.getAllItems());
        request.getRequestDispatcher("/WEB-INF/page/AdminMenu.jsp").forward(request, response);
    }

    // ── POST: route to the correct action based on the URL path ──────────────


    //  POST — dispatch to add, update, or delete based on the request path.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reject non-admin users before processing any changes
        if (!isAdmin(request, response)) return;

        // Determine which CRUD operation was requested
        String path = request.getServletPath();

        switch (path) {
            case "/admin/menu/add":    addItem(request, response);    break; // create a new item
            case "/admin/menu/update": updateItem(request, response); break; // edit an existing item
            case "/admin/menu/delete": deleteItem(request, response); break; // remove an item
            default:
                // Unknown path — fall back to the menu list
                response.sendRedirect(request.getContextPath() + "/admin/menu");
        }
    }

    //  ADD: create a new menu item with an optional image

    //Validates the add form, saves the image, and inserts the new item into the database.
    private void addItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Read the new item's details from the submitted form
        String name        = request.getParameter("itemName");
        String category    = request.getParameter("category");
        String priceParam  = request.getParameter("price");
        String description = request.getParameter("description");

        // Reject the form if any required field is missing
        if (name == null || name.isBlank() || category == null || priceParam == null) {
            request.setAttribute("error", "Please fill all required fields.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/WEB-INF/page/AdminMenu.jsp").forward(request, response);
            return;
        }

        try {
            // Parse the price string to a double; throws if the value is not numeric
            double price = Double.parseDouble(priceParam.trim());

            // Save the uploaded image file and get back its relative URL
            String imageUrl = saveUploadedImage(request, "imageFile");

            // Build the MenuItem object and persist it to the database
            MenuItem item = new MenuItem(name.trim(), category.trim().toLowerCase(),
                                         price, description, imageUrl);
            menuDAO.addItem(item);

            // Redirect to the menu list with a success flag
            response.sendRedirect(request.getContextPath() + "/admin/menu?success=added");

        } catch (NumberFormatException e) {
            // Price field contained non-numeric text — show an error
            request.setAttribute("error", "Invalid price value.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/WEB-INF/page/AdminMenu.jsp").forward(request, response);
        }
    }

    // ── UPDATE: edit an existing menu item ───────────────────────────────────

    /**
     * Validates the edit form, optionally replaces the image, and updates the database record.
     */
    private void updateItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Read all editable fields from the form
        String idParam       = request.getParameter("itemId");
        String name          = request.getParameter("itemName");
        String category      = request.getParameter("category");
        String priceParam    = request.getParameter("price");
        String description   = request.getParameter("description");
        String existingImage = request.getParameter("existingImage"); // current image path, kept if no new upload

        // Reject the form if required fields are missing
        if (idParam == null || name == null || name.isBlank() || priceParam == null) {
            request.setAttribute("error", "Please fill all required fields.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/WEB-INF/page/AdminMenu.jsp").forward(request, response);
            return;
        }

        try {
            int    id    = Integer.parseInt(idParam.trim());   // ID of the item to update
            double price = Double.parseDouble(priceParam.trim());

            // Default to the existing image; only replace it if a new file was uploaded
            String imageUrl = existingImage;
            Part filePart = request.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                // A new image was provided — save it and use the new path
                String uploaded = saveUploadedImage(request, "imageFile");
                if (uploaded != null) imageUrl = uploaded;
            }

            // Build the updated MenuItem and persist the changes
            MenuItem item = new MenuItem(name.trim(), category.trim().toLowerCase(),
                                         price, description, imageUrl);
            item.setId(id); // set the ID so the DAO knows which record to update
            menuDAO.updateItem(item);

            // Redirect to the menu list with a success flag
            response.sendRedirect(request.getContextPath() + "/admin/menu?success=updated");

        } catch (NumberFormatException e) {
            // ID or price field contained non-numeric text — show an error
            request.setAttribute("error", "Invalid ID or price.");
            request.setAttribute("menuItems", menuDAO.getAllItems());
            request.getRequestDispatcher("/WEB-INF/page/AdminMenu.jsp").forward(request, response);
        }
    }

    // ── DELETE: remove a menu item by ID ─────────────────────────────────────

    /**
     * Parses the item ID and deletes the corresponding record from the database.
     */
    private void deleteItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idParam = request.getParameter("itemId");

        // Reject the request if no item ID was provided
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/menu?error=missingId");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim()); // parse the item ID to delete
            menuDAO.deleteItem(id);                    // remove the item from the database
            response.sendRedirect(request.getContextPath() + "/admin/menu?success=deleted");
        } catch (NumberFormatException e) {
            // ID was not a valid integer — redirect with an error flag
            response.sendRedirect(request.getContextPath() + "/admin/menu?error=invalidId");
        }
    }

    // ── IMAGE UPLOAD

    /**
     * Saves the uploaded image file to /Resource/menu/ and returns its relative path.
     * Returns the default image path if no file was uploaded or the save fails.
     */
    private String saveUploadedImage(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {

        Part filePart = request.getPart(fieldName);

        // If no file was selected, use the default placeholder image
        if (filePart == null || filePart.getSize() == 0) {
            return "/Resource/default.jpg";
        }

        // Delegate the actual file-saving to UploadUtil; fall back to default on failure
        String saved = UploadUtil.save(filePart, getServletContext(), "menu", "item");
        return saved != null ? saved : "/Resource/default.jpg";
    }

    // ── ADMIN

    /**
     * Checks whether the current user is an admin.
     * Redirects to login and returns false if they are not.
     */
    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // caller should stop processing immediately
        }
        return true;
    }
}

package aptProject.Controller.servlets;

import aptProject.dao.UserDAO;
import aptProject.model.User;
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
 * AdminProfileServlet — lets the admin view and update their own profile.
 * Only accessible to users with the ADMIN role; others are redirected to login.
 * Supports multipart form data so a profile image can be uploaded alongside text fields.
 */
@WebServlet(name = "AdminProfileServlet", urlPatterns = {"/admin/profile", "/admin/profile/update"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // keep files up to 1 MB in memory before writing to disk
        maxFileSize       = 5 * 1024 * 1024,  // reject individual files larger than 5 MB
        maxRequestSize    = 10 * 1024 * 1024  // reject entire requests larger than 10 MB
)
public class AdminProfileServlet extends HttpServlet {

    // DAO used to read and update admin user records in the database
    private final UserDAO userDAO = new UserDAO();

    /**
     * GET — display the admin profile page.
     * Shows a success toast if the page was reached after a successful save.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Block non-admin users from accessing this page
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // If redirected here after a save, set a success message for the JSP to display
        if ("true".equals(request.getParameter("saved"))) {
            request.setAttribute("success", "Profile updated successfully.");
        }

        // Pass the current admin's data to the JSP for display
        request.setAttribute("adminUser", SessionUtil.getUser(request));
        request.getRequestDispatcher("/WEB-INF/page/AdminProfile.jsp").forward(request, response);
    }

    /**
     * POST — save the admin's updated profile details.
     * Handles both text fields (name, email) and an optional profile image upload.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Block non-admin users from submitting this form
        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Read the updated name and email from the submitted form
        String fullName = request.getParameter("adminName");
        String email    = request.getParameter("adminEmail");

        // Get the current admin object from the session and apply the new values
        User admin = SessionUtil.getUser(request);
        admin.setFullName(fullName);
        admin.setEmail(email);

        // Persist the updated name and email to the database
        userDAO.updateProfile(admin.getId(), fullName, email);

        // Check if a new profile image was included in the upload
        Part imagePart = request.getPart("profileImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            // Save the uploaded image file and get back its relative path
            String imagePath = UploadUtil.save(imagePart, getServletContext(),
                                               "profiles", "admin_" + admin.getId());
            if (imagePath != null) {
                // Update the in-memory user object and persist the new image path
                admin.setProfileImage(imagePath);
                userDAO.updateProfileImage(admin.getId(), imagePath);
            }
        }

        // Refresh the session so the updated user data is immediately available
        SessionUtil.createSession(request, admin);

        // redirect to GET to prevent re-submission on browser refresh
        response.sendRedirect(request.getContextPath() + "/admin/profile?saved=true");
    }

}

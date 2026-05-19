package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.dao.UserDAO;
import aptProject.model.User;
import aptProject.utilities.PasswordUtil;
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
 * UserProfileServlet — lets a logged-in user view their profile, update their details, and change their password.
 *   GET  /profile                  → display the user profile page with order history
 *   POST /profile/update           → save updated name, email, and optional profile image
 *   POST /profile/changePassword   → validate and update the user's password
 */
@WebServlet(name = "UserProfileServlet",
        urlPatterns = {"/profile", "/profile/changePassword", "/profile/update"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // keep files up to 1 MB in memory before writing to disk
        maxFileSize       = 5 * 1024 * 1024,  // reject individual files larger than 5 MB
        maxRequestSize    = 10 * 1024 * 1024  // reject entire requests larger than 10 MB
)
public class UserProfileServlet extends HttpServlet {

    // Route constants — avoids repeating magic strings throughout the class
    private static final String UPDATE_PROFILE  = "/profile/update";
    private static final String CHANGE_PASSWORD = "/profile/changePassword";

    // DAOs for reading/writing user records and fetching order history
    private final UserDAO  userDAO  = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();

     // GET — display the user profile page.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Authentication handled by AuthFilter

        // Show a success banner if the page was reached after a successful profile save
        if ("true".equals(request.getParameter("saved"))) {
            request.setAttribute("success", "Profile updated successfully.");
        }

        // Load the current user and their orders, then forward to the profile JSP
        User user = SessionUtil.getUser(request);
        request.setAttribute("profileUser", user);
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId())); // order history list
        request.getRequestDispatcher("/WEB-INF/page/UserProfile.jsp").forward(request, response);
    }

     // POST — route the request to the correct handler based on the URL path.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Authentication handled by AuthFilter

        // Determine which sub-action was requested by inspecting the URL path
        String path = request.getServletPath();

        if (UPDATE_PROFILE.equals(path)) {
            // Handle name, email, and profile image update
            handleProfileUpdate(request, response);
        } else if (CHANGE_PASSWORD.equals(path)) {
            // Handle password change with current-password verification
            handlePasswordChange(request, response);
        } else {
            // Unknown path — fall back to the profile page
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    // ── UPDATE NAME / EMAIL / PROFILE IMAGE ──────────────────────────────────

     // Saves the user's updated name, email, and optional profile image.

    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Apply the new name and email to the in-memory user object
        User user = SessionUtil.getUser(request);
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));

        // Persist the updated name and email to the database
        userDAO.updateProfile(user.getId(), user.getFullName(), user.getEmail());

        // Only process the image if the user actually selected a file
        Part imagePart = request.getPart("profileImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            // Save the uploaded image and get back its relative path
            String imagePath = UploadUtil.save(
                    imagePart,
                    getServletContext(),
                    "profiles",
                    "user_" + user.getId()
            );
            if (imagePath != null) {
                // Update the in-memory user and persist the new image path
                user.setProfileImage(imagePath);
                userDAO.updateProfileImage(user.getId(), imagePath);
            }
        }

        // Refresh the session so the updated user data is immediately available
        SessionUtil.createSession(request, user);

        // PRG pattern: redirect to GET to prevent re-submission on browser refresh
        response.sendRedirect(request.getContextPath() + "/profile?saved=true");
    }

    // ── CHANGE PASSWORD ───────────────────────────────────────────────────────

     // Validates the current password, then saves the new hashed password.

    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read all three password fields from the form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword     = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        User user = SessionUtil.getUser(request);

        // Verify the current password matches the stored hash before allowing a change
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
            request.setAttribute("error", "Current password is incorrect.");
            forwardToProfile(request, response, user);
            return;
        }

        // Ensure the new password and its confirmation are identical
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match.");
            forwardToProfile(request, response, user);
            return;
        }

        // Hash the new password and save it to the database
        String hashed = PasswordUtil.hashPassword(newPassword);
        userDAO.updatePassword(user.getId(), hashed);

        // Update the in-memory user and refresh the session with the new hash
        user.setPasswordHash(hashed);
        SessionUtil.createSession(request, user);

        // Show a success message on the profile page
        request.setAttribute("success", "Password updated successfully.");
        forwardToProfile(request, response, user);
    }

     //Helper — loads the user's orders and forwards to the profile JSP.Used by both the password-change handler and error paths.
    private void forwardToProfile(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        // Attach the user and their order history so the JSP can render them
        request.setAttribute("profileUser", user);
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
        request.getRequestDispatcher("/WEB-INF/page/UserProfile.jsp").forward(request, response);
    }
}

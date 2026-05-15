package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.dao.UserDAO;
import aptProject.model.User;
import aptProject.utilities.PasswordUtil;
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

@WebServlet(name = "UserProfileServlet",
        urlPatterns = {"/profile", "/profile/changePassword", "/profile/update"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize       = 5 * 1024 * 1024,
        maxRequestSize    = 10 * 1024 * 1024
)
public class UserProfileServlet extends HttpServlet {

    private final UserDAO  userDAO  = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Show toast if redirected after a successful save
        if ("true".equals(request.getParameter("saved"))) {
            request.setAttribute("success", "Profile updated successfully.");
        }

        User user = SessionUtil.getUser(request);
        request.setAttribute("profileUser", user);
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
        request.getRequestDispatcher("/page/UserProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        if ("/profile/update".equals(path)) {
            handleProfileUpdate(request, response);
        } else {
            // /profile/changePassword (and legacy /profile/uploadImage)
            handlePasswordChange(request, response);
        }
    }

    // ── UPDATE NAME / EMAIL / PROFILE IMAGE ──────────────────────────────────
    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email    = request.getParameter("email");

        User user = SessionUtil.getUser(request);
        user.setFullName(fullName);
        user.setEmail(email);

        userDAO.updateProfile(user.getId(), fullName, email);

        // Handle optional profile image upload
        Part imagePart = request.getPart("profileImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String imagePath = saveProfileImage(imagePart, user.getId());
            if (imagePath != null) {
                user.setProfileImage(imagePath);
                userDAO.updateProfileImage(user.getId(), imagePath);
            }
        }

        // Refresh session with updated data
        SessionUtil.createSession(request, user);

        // PRG redirect with success flag
        response.sendRedirect(request.getContextPath() + "/profile?saved=true");
    }

    // ── CHANGE PASSWORD ───────────────────────────────────────────────────────
    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword     = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        User user = SessionUtil.getUser(request);

        if (!PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
            request.setAttribute("error", "Current password is incorrect.");
            forwardToProfile(request, response, user);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match.");
            forwardToProfile(request, response, user);
            return;
        }

        String hashed = PasswordUtil.hashPassword(newPassword);
        userDAO.updatePassword(user.getId(), hashed);
        user.setPasswordHash(hashed);
        SessionUtil.createSession(request, user);

        request.setAttribute("success", "Password updated successfully.");
        forwardToProfile(request, response, user);
    }

    private void forwardToProfile(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        request.setAttribute("profileUser", user);
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
        request.getRequestDispatcher("/page/UserProfile.jsp").forward(request, response);
    }

    /** Save uploaded image to /Resource/profiles/ and return the web path */
    private String saveProfileImage(Part part, int userId) {
        try {
            String originalName = part.getSubmittedFileName();
            if (originalName == null || originalName.isBlank()) return null;

            String ext      = originalName.substring(originalName.lastIndexOf('.'));
            String fileName = "user_" + userId + "_" + System.currentTimeMillis() + ext;

            String uploadDir = getServletContext().getRealPath("/Resource/profiles");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            try (InputStream in = part.getInputStream()) {
                Files.copy(in, Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);
            }

            return "../Resource/profiles/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

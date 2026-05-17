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

@WebServlet(name = "UserProfileServlet",
        urlPatterns = {"/profile", "/profile/changePassword", "/profile/update"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize       = 5 * 1024 * 1024,
        maxRequestSize    = 10 * 1024 * 1024
)
public class UserProfileServlet extends HttpServlet {

    // ── Route constants — avoids magic strings scattered through the code ──
    private static final String UPDATE_PROFILE   = "/profile/update";
    private static final String CHANGE_PASSWORD  = "/profile/changePassword";

    private final UserDAO  userDAO  = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if ("true".equals(request.getParameter("saved"))) {
            request.setAttribute("success", "Profile updated successfully.");
        }

        User user = SessionUtil.getUser(request);
        request.setAttribute("profileUser", user);
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
        request.getRequestDispatcher("/WEB-INF/page/UserProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        if (UPDATE_PROFILE.equals(path)) {
            handleProfileUpdate(request, response);
        } else if (CHANGE_PASSWORD.equals(path)) {
            handlePasswordChange(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    // ── UPDATE NAME / EMAIL / PROFILE IMAGE ──────────────────────────────────
    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = SessionUtil.getUser(request);
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));

        userDAO.updateProfile(user.getId(), user.getFullName(), user.getEmail());

        // Null-safe image upload — only saves if a file was actually selected
        Part imagePart = request.getPart("profileImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String imagePath = UploadUtil.save(
                    imagePart,
                    getServletContext(),
                    "profiles",
                    "user_" + user.getId()
            );
            if (imagePath != null) {
                user.setProfileImage(imagePath);
                userDAO.updateProfileImage(user.getId(), imagePath);
            }
        }

        SessionUtil.createSession(request, user);
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
        request.getRequestDispatcher("/WEB-INF/page/UserProfile.jsp").forward(request, response);
    }
}

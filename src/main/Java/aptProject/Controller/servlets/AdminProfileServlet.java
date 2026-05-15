package aptProject.Controller.servlets;

import aptProject.dao.UserDAO;
import aptProject.model.User;
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
 * AdminProfileServlet
 *
 * GET  /admin/profile         → show AdminProfile.jsp
 * POST /admin/profile/update  → save name, email, and optional profile image
 */
@WebServlet(name = "AdminProfileServlet", urlPatterns = {"/admin/profile", "/admin/profile/update"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize       = 5 * 1024 * 1024,
        maxRequestSize    = 10 * 1024 * 1024
)
public class AdminProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Show toast if redirected after a successful save
        if ("true".equals(request.getParameter("saved"))) {
            request.setAttribute("success", "Profile updated successfully.");
        }

        request.setAttribute("adminUser", SessionUtil.getUser(request));
        request.getRequestDispatcher("/page/AdminProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String fullName = request.getParameter("adminName");
        String email    = request.getParameter("adminEmail");

        User admin = SessionUtil.getUser(request);
        admin.setFullName(fullName);
        admin.setEmail(email);

        // Save name and email
        userDAO.updateProfile(admin.getId(), fullName, email);

        // Handle optional profile image upload
        Part imagePart = request.getPart("profileImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String imagePath = saveProfileImage(imagePart, admin.getId());
            if (imagePath != null) {
                admin.setProfileImage(imagePath);
                userDAO.updateProfileImage(admin.getId(), imagePath);
            }
        }

        // Refresh session with updated data
        SessionUtil.createSession(request, admin);

        // Redirect back to profile page with success flag (PRG pattern — clears the form hash)
        response.sendRedirect(request.getContextPath() + "/admin/profile?saved=true");
    }

    /** Save uploaded image to /Resource/profiles/ and return the web path */
    private String saveProfileImage(Part part, int userId) {
        try {
            String originalName = part.getSubmittedFileName();
            if (originalName == null || originalName.isBlank()) return null;

            // Sanitize and prefix with userId so each admin has a unique file
            String ext      = originalName.substring(originalName.lastIndexOf('.'));
            String fileName = "admin_" + userId + "_" + System.currentTimeMillis() + ext;

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

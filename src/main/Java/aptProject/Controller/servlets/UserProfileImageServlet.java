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
 * UserProfileImageServlet
 *
 * POST /profile/uploadImage  → upload a new profile picture for the logged-in user
 */
@WebServlet(name = "UserProfileImageServlet", urlPatterns = {"/profile/uploadImage"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize       = 5 * 1024 * 1024,
        maxRequestSize    = 10 * 1024 * 1024
)
public class UserProfileImageServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = SessionUtil.getUser(request);

        Part imagePart = request.getPart("profileImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String imagePath = saveProfileImage(imagePart, user.getId());
            if (imagePath != null) {
                user.setProfileImage(imagePath);
                userDAO.updateProfileImage(user.getId(), imagePath);
                SessionUtil.createSession(request, user);
            }
        }

        response.sendRedirect(request.getContextPath() + "/profile?saved=true");
    }

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

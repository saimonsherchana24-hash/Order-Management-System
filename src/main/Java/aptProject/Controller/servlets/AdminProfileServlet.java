package aptProject.Controller.servlets;

import aptProject.dao.UserDAO;
import aptProject.model.User;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminProfileServlet", urlPatterns = {"/admin/profile", "/admin/profile/update"})
public class AdminProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        request.setAttribute("adminUser", SessionUtil.getUser(request));
        request.getRequestDispatcher("/page/AdminProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        String fullName = request.getParameter("adminName");
        String email    = request.getParameter("adminEmail");

        User admin = SessionUtil.getUser(request);
        admin.setFullName(fullName);
        admin.setEmail(email);

        // Save to DB and refresh session
        userDAO.updateProfile(admin.getId(), fullName, email);
        SessionUtil.createSession(request, admin);

        request.setAttribute("success",   "Profile updated successfully.");
        request.setAttribute("adminUser", admin);
        request.getRequestDispatcher("/page/AdminProfile.jsp").forward(request, response);
    }
}

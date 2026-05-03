package aptProject.Controller.servlets;

import aptProject.dao.Interface.UserDAOInterface;
import aptProject.dao.UserDAO;
import aptProject.model.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/page/login"})
public class LoginServlet extends HttpServlet {
    private final UserDAOInterface userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usernameOrEmail = request.getParameter("username");
        String password = request.getParameter("password");

        User userObj = userDAO.login(usernameOrEmail, password);

        if (userObj != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userObj);
            session.setAttribute("userId", userObj.getId());
            session.setAttribute("username", userObj.getUsername());

            // 1. SAFETY CHECK: If the role in the database is null or empty, default to "USER"
            String role = userObj.getRole();
            if (role == null || role.trim().isEmpty()) {
                role = "USER";
            }

            session.setAttribute("userRole", role);

            // 2. DEBUG PRINT: Look at your IntelliJ console when you click login!
            System.out.println(">>> LOGIN CHECK: The role for " + userObj.getUsername() + " is [" + role + "]");

            // 3. REDIRECT LOGIC
            if ("ADMIN".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/page/AdminDashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/page/home.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/page/login.jsp").forward(request, response);
        }
    }
}
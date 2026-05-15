package aptProject.Controller.servlets;

import aptProject.dao.UserDAO;
import aptProject.model.User;
import aptProject.utilities.CookieUtil;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "LoginServlet", urlPatterns = {"/page/login"})
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If already logged in, skip login page
        if (SessionUtil.isLoggedIn(request)) {
            redirectByRole(request, response);
            return;
        }

        // Pre-fill username if Remember Me cookie exists
        String remembered = CookieUtil.getRememberedUsername(request);
        request.setAttribute("rememberedUsername", remembered);

        request.getRequestDispatcher("/page/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usernameOrEmail = request.getParameter("username");
        String password        = request.getParameter("password");
        String rememberMe      = request.getParameter("rememberMe");

        // Try to log in
        User user = userDAO.login(usernameOrEmail, password);

        if (user != null) {
            SessionUtil.createSession(request, user);

            // Handle Remember Me checkbox
            if ("on".equals(rememberMe)) {
                CookieUtil.setRememberMe(response, user.getUsername());
            } else {
                CookieUtil.clearRememberMe(response);
            }

            redirectByRole(request, response);

        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/page/Login.jsp").forward(request, response);
        }
    }

    /** Send ADMIN to dashboard, USER to home page */
    private void redirectByRole(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/page/home.jsp");
        }
    }
}

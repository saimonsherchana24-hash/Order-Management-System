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

/**
 *   GET  /login        → display the login form
 *   GET  /page/login   → alternate path to the same login form
 *   POST /login        → validate credentials and start a session
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/page/login"})
public class LoginServlet extends HttpServlet {

    // DAO used to look up and authenticate users from the database
    private final UserDAO userDAO = new UserDAO();

    /**
     * GET — show the login page.
     * Skips the form if the user is already logged in and redirects by role instead.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the user already has an active session, skip the login page entirely
        if (SessionUtil.isLoggedIn(request)) {
            redirectByRole(request, response);
            return;
        }

        // Pass the redirect parameter to the JSP so the POST form can include it
        String redirect = request.getParameter("redirect");
        if (redirect != null) request.setAttribute("redirect", redirect);

        // Pre-fill the username field if a "Remember Me" cookie is present
        String remembered = CookieUtil.getRememberedUsername(request);
        request.setAttribute("rememberedUsername", remembered);

        // Forward to the login JSP page
        request.getRequestDispatcher("/WEB-INF/page/Login.jsp").forward(request, response);
    }

    /**
     * POST — process the submitted login form.
     * Validates credentials, creates a session, handles Remember Me, and redirects.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read form fields submitted by the user
        String usernameOrEmail = request.getParameter("username");
        String password        = request.getParameter("password");
        String rememberMe      = request.getParameter("rememberMe");
        String redirect        = request.getParameter("redirect"); // optional post-login destination

        // Attempt to authenticate the user against the database
        User user = userDAO.login(usernameOrEmail, password);

        // If credentials are valid, set up the session and redirect
        if (user != null) {
            // Create a new HTTP session and store the authenticated user in it
            SessionUtil.createSession(request, user);

            // If "Remember Me" was checked, save the username in a persistent cookie
            if ("on".equals(rememberMe)) {
                CookieUtil.setRememberMe(response, user.getUsername());
            } else {
                // Otherwise clear any existing Remember Me cookie
                CookieUtil.clearRememberMe(response);
            }

            // If a specific redirect target was requested and the user is not an admin, honour it
            if ("menu".equals(redirect) && !SessionUtil.isAdmin(request)) {
                response.sendRedirect(request.getContextPath() + "/menu");
            } else {
                // Default: send admin to dashboard, regular user to menu
                redirectByRole(request, response);
            }

        } else {
            // Login failed — show an error message and re-display the form
            request.setAttribute("error", "Invalid username or password.");
            // Preserve the redirect param so the form can pass it on the next attempt
            if (redirect != null) request.setAttribute("redirect", redirect);
            request.getRequestDispatcher("/WEB-INF/page/Login.jsp").forward(request, response);
        }
    }

    /**
     * Redirects the current user to the appropriate home page based on their role.
     * ADMIN → /admin/dashboard
     * USER  → /menu
     */
    private void redirectByRole(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Check the role stored in the session and redirect accordingly
        if (SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/menu");
        }
    }
}

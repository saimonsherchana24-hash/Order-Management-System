package aptProject.Controller.servlets;

import aptProject.utilities.CookieUtil;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *   GET  /logout → invalidate session, clear Remember Me cookie, redirect to login
 *   POST /logout → delegates to doGet (same behaviour)
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    //GET — perform the logout sequence.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Invalidate the current HTTP session, removing all stored user data
        SessionUtil.destroySession(request);

        // Delete the "Remember Me" cookie so the browser won't auto-fill credentials
        CookieUtil.clearRememberMe(response);

        // Send the user back to the login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
     // POST — same as GET; allows logout via form submission as well as a link.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Reuse the GET handler so both HTTP methods behave identically
        doGet(request, response);
    }
}

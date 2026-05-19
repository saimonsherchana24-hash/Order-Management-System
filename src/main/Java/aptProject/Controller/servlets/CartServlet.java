package aptProject.Controller.servlets;

import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * CartServlet — displays the shopping cart page.
 * Only accessible to authenticated users; unauthenticated requests are redirected to login.
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
     // GET — verify the user is logged in, then display the cart page.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redirect unauthenticated users to the login page before showing the cart
        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // User is authenticated — forward to the cart JSP (cart data is in localStorage)
        request.getRequestDispatcher("/WEB-INF/page/cart.jsp").forward(request, response);
    }
}

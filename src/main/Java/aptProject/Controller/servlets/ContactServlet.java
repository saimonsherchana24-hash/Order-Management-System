package aptProject.Controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * ContactServlet — serves the static "Contact Us" page.
 *GET /contact → forward to Contact.jsp
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {

    //GET — forward directly to the Contact Us JSP page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // No data preparation needed — just forward to the static Contact page
        request.getRequestDispatcher("/WEB-INF/page/Contact.jsp").forward(request, response);
    }
}

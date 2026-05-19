package aptProject.Controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//AboutServlet — serves the static "About Us" page and contains only static content rendered by the JSP.

@WebServlet(name = "AboutServlet", urlPatterns = {"/about"})
public class AboutServlet extends HttpServlet {

    /*
     * GET — forward directly to the About Us JSP page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // No data preparation needed — just forward to the static About page
        request.getRequestDispatcher("/WEB-INF/page/About.jsp").forward(request, response);
    }
}

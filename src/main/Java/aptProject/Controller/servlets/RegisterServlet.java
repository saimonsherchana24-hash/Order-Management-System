package aptProject.Controller.servlets;

import aptProject.model.User;
import aptProject.utilities.PasswordUtil;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAOInterface();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String terms = request.getParameter("terms");

        if (fullName == null || email == null || password == null
                || fullName.isBlank() || email.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Please fill all required fields.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response);
            return;
        }

        if (terms == null) {
            request.setAttribute("error", "Please agree to the Terms of Service and Privacy Policy.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response);
            return;
        }

        if (userDAO.findByEmail(email) != null) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response);
            return;
        }

        String username = generateUsername();
        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User(fullName, username, email, hashedPassword, "USER");

        if (userDAO.register(user)) {
            response.sendRedirect(request.getContextPath() + "/page/login.jsp?registered=true");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response);
        }
    }

    private String generateUsername() {
        String username;

        do {
            int number = ThreadLocalRandom.current().nextInt(10000, 100000);
            username = "user" + number;
        } while (userDAO.findByUsername(username) != null);

        return username;
    }
}
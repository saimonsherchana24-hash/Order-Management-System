package aptProject.Controller.servlets;

import aptProject.dao.UserDAO;
import aptProject.dao.Interface.UserDAOInterface;
import aptProject.model.User;
import aptProject.utilities.PasswordUtil;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private final UserDAOInterface userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String terms = request.getParameter("terms");

        // 1. Check empty fields
        if (fullName == null || email == null || password == null
                || fullName.isBlank() || email.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Please fill all required fields.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response); // FIXED PATH
            return;
        }

        // 2. Check password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response); // FIXED PATH
            return;
        }

        // 3. Check terms
        if (terms == null) {
            request.setAttribute("error", "Please agree to the Terms of Service and Privacy Policy.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response); // FIXED PATH
            return;
        }

        // 4. Check if email exists
        if (userDAO.findByEmail(email) != null) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/page/register.jsp").forward(request, response); // FIXED PATH
            return;
        }

        // 5. Prepare User Object
        String username = generateUsername();
        String savedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(savedPassword);
        user.setRole("USER");

        // DEBUG: Print to console to verify data before saving
        System.out.println("===== SAVING USER TO DB =====");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Hashed Password: " + user.getPasswordHash());

        // 6. Save to Database
        if (userDAO.register(user)) {
            System.out.println("SUCCESS: User saved to database!");
            response.sendRedirect(request.getContextPath() + "/page/login.jsp?registered=true"); // FIXED PATH
        } else {
            System.out.println("FAILURE: userDAO.register() returned false!");
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
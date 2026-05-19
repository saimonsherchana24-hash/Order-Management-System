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

/**
 * RegisterServlet — handles new user account creation.
 *   GET  /register → display the registration form
 *   POST /register → validate input, create the account, redirect to login
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    // DAO used to check for existing users and persist the new account
    private final UserDAOInterface userDAO = new UserDAO();

     // GET — show the empty registration form.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward directly to the registration JSP — no data needed
        request.getRequestDispatcher("/WEB-INF/page/UserRegister.jsp").forward(request, response);
    }

     // POST — validate the submitted form and create the new user account.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read all form fields submitted by the user
        String fullName       = request.getParameter("fullName");
        String email          = request.getParameter("email");
        String password       = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String terms          = request.getParameter("terms"); // null if checkbox was not checked

        // 1. Reject the form if any required field is missing or blank
        if (fullName == null || email == null || password == null
                || fullName.isBlank() || email.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Please fill all required fields.");
            request.getRequestDispatcher("/WEB-INF/page/UserRegister.jsp").forward(request, response);
            return;
        }

        // 2. Ensure the password and its confirmation are identical
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/page/UserRegister.jsp").forward(request, response);
            return;
        }

        // 3. Require the user to accept the Terms of Service
        if (terms == null) {
            request.setAttribute("error", "Please agree to the Terms of Service and Privacy Policy.");
            request.getRequestDispatcher("/WEB-INF/page/UserRegister.jsp").forward(request, response);
            return;
        }

        // 4. Prevent duplicate accounts — check if the email is already registered
        if (userDAO.findByEmail(email) != null) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/WEB-INF/page/UserRegister.jsp").forward(request, response);
            return;
        }

        // 5. Build the new User object with a generated username and hashed password
        String username     = generateUsername();              // auto-generated unique username
        String savedPassword = PasswordUtil.hashPassword(password); // bcrypt hash of the plain password

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(savedPassword);
        user.setRole("USER"); // all self-registered accounts start as regular users

        // DEBUG: Print registration details to the server console for verification
        System.out.println("===== SAVING USER TO DB =====");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Hashed Password: " + user.getPasswordHash());

        // 6. Persist the user to the database and redirect or show an error
        if (userDAO.register(user)) {
            System.out.println("SUCCESS: User saved to database!");
            // Redirect to login with a flag so the page can show a success message
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } else {
            System.out.println("FAILURE: userDAO.register() returned false!");
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/WEB-INF/page/UserRegister.jsp").forward(request, response);
        }
    }

     //Generates a unique username in the format "user#####".

    private String generateUsername() {
        String username;
        do {
            // Pick a random number between 10000 and 99999 to append to "user"
            int number = ThreadLocalRandom.current().nextInt(10000, 100000);
            username = "user" + number;
        } while (userDAO.findByUsername(username) != null); // retry if the username already exists
        return username;
    }
}

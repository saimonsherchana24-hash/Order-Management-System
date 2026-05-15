package aptProject.Controller.servlets;

import aptProject.dao.OrderDAO;
import aptProject.dao.UserDAO;
import aptProject.model.User;
import aptProject.utilities.PasswordUtil;
import aptProject.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserProfileServlet", urlPatterns = {"/profile", "/profile/changePassword"})
public class UserProfileServlet extends HttpServlet {

    private final UserDAO  userDAO  = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        User user = SessionUtil.getUser(request);
        request.setAttribute("profileUser", user);

        // Load this user's orders and pass to JSP
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));

        request.getRequestDispatcher("/page/UserProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/page/Login.jsp");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword     = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        User user = SessionUtil.getUser(request);

        // Check current password is correct
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
            request.setAttribute("error", "Current password is incorrect.");
            request.setAttribute("profileUser", user);
            request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
            request.getRequestDispatcher("/page/UserProfile.jsp").forward(request, response);
            return;
        }

        // Check new passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match.");
            request.setAttribute("profileUser", user);
            request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
            request.getRequestDispatcher("/page/UserProfile.jsp").forward(request, response);
            return;
        }

        // Save new password
        String hashed = PasswordUtil.hashPassword(newPassword);
        userDAO.updatePassword(user.getId(), hashed);

        request.setAttribute("success", "Password updated successfully.");
        request.setAttribute("profileUser", user);
        request.setAttribute("myOrders", orderDAO.getOrdersByUserId(user.getId()));
        request.getRequestDispatcher("/page/UserProfile.jsp").forward(request, response);
    }
}

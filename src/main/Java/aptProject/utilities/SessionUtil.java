package aptProject.utilities;

import aptProject.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * SessionUtil - manages login session data.
 * Called by every servlet to read/write the logged-in user.
 */
public final class SessionUtil {

    private SessionUtil() {}

    /** Save user data into session after login */
    public static void createSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user",     user);
        session.setAttribute("userId",   user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userRole", user.getRole());
    }

    /** Clear session on logout */
    public static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
    }

    /** Get the logged-in User object */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("user");
    }

    /** Get the logged-in user's ID */
    public static int getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return -1;
        Object id = session.getAttribute("userId");
        return (id instanceof Integer) ? (Integer) id : -1;
    }

    /** Check if someone is logged in */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null;
    }

    /** Check if the logged-in user is an ADMIN */
    public static boolean isAdmin(HttpServletRequest request) {
        User user = getUser(request);
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }
}

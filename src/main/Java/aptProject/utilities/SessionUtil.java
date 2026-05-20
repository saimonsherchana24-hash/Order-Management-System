package aptProject.utilities;

import aptProject.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * SessionUtil — utility class for managing the HTTP session after login.
 *
 * <p>Centralises all session read/write operations so that every servlet
 * accesses session data through a consistent API rather than using raw
 * attribute strings scattered across the codebase.</p>
 *
 * <p>Session attributes stored:</p>
 * <ul>
 *   <li>{@code "user"}     — the full {@link User} object</li>
 *   <li>{@code "userId"}   — the user's integer ID</li>
 *   <li>{@code "username"} — the user's login handle</li>
 *   <li>{@code "userRole"} — "USER" or "ADMIN"</li>
 * </ul>
 */
public final class SessionUtil {

    /** Private constructor — this class should never be instantiated. */
    private SessionUtil() {}

    /**
     * Creates (or replaces) the session and stores the logged-in user's data.
     * Called immediately after a successful login.
     *
     * @param request the current HTTP request (used to access the session)
     * @param user    the authenticated {@link User} whose data should be stored
     */
    public static void createSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(); // create session if it doesn't exist

        // Store individual attributes so JSPs can access them without casting
        session.setAttribute("user",     user);              // full User object
        session.setAttribute("userId",   user.getId());      // integer ID for quick lookups
        session.setAttribute("username", user.getUsername()); // display name in nav bar
        session.setAttribute("userRole", user.getRole());    // role for access control checks
    }

    /**
     * Invalidates the current session, effectively logging the user out.
     * Safe to call even if no session exists.
     *
     * @param request the current HTTP request
     */
    public static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false = don't create a new session
        if (session != null) session.invalidate();       // clear all attributes and end the session
    }

    /**
     * Returns the currently logged-in {@link User} from the session.
     *
     * @param request the current HTTP request
     * @return the logged-in {@link User}, or {@code null} if no session exists
     */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false = don't create a new session
        if (session == null) return null;                // no active session
        return (User) session.getAttribute("user");      // cast the stored object back to User
    }

    /**
     * Returns the ID of the currently logged-in user.
     *
     * @param request the current HTTP request
     * @return the user's integer ID, or {@code -1} if not logged in
     */
    public static int getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false = don't create a new session
        if (session == null) return -1;                  // no active session

        Object id = session.getAttribute("userId");
        // Check type before casting to avoid ClassCastException
        return (id instanceof Integer) ? (Integer) id : -1;
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @param request the current HTTP request
     * @return {@code true} if a valid session with a User object exists; {@code false} otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null; // logged in if a User object is present in the session
    }

    /**
     * Checks whether the currently logged-in user has the ADMIN role.
     *
     * @param request the current HTTP request
     * @return {@code true} if the user is logged in and has role "ADMIN"; {@code false} otherwise
     */
    public static boolean isAdmin(HttpServletRequest request) {
        User user = getUser(request);
        // Null-safe role check — equalsIgnoreCase handles mixed-case role values
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }
}

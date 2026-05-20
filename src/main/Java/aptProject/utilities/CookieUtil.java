package aptProject.utilities;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CookieUtil — utility class for managing the "Remember Me" cookie on the login page.
 *
 * <p>When a user ticks "Remember Me" at login, their username is saved in a
 * browser cookie that persists for 7 days. On the next visit the login form
 * is pre-filled with the saved username. The cookie stores only the username —
 * never the password or session token.</p>
 */
public final class CookieUtil {

    /** The name of the remember-me cookie stored in the browser */
    private static final String REMEMBER_ME = "rememberedUser";

    /** Cookie lifetime: 7 days expressed in seconds */
    private static final int    SEVEN_DAYS  = 7 * 24 * 60 * 60;

    /** Private constructor — this class should never be instantiated. */
    private CookieUtil() {}

    /**
     * Creates a "Remember Me" cookie containing the username and adds it to the response.
     * The cookie persists in the browser for 7 days.
     *
     * @param response the HTTP response to attach the cookie to
     * @param username the username to save in the cookie
     */
    public static void setRememberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(REMEMBER_ME, username); // create cookie with username value
        cookie.setMaxAge(SEVEN_DAYS);  // cookie expires after 7 days
        cookie.setPath("/");           // cookie is sent for all paths in this application
        cookie.setHttpOnly(true);      // prevent JavaScript from reading the cookie (XSS protection)
        response.addCookie(cookie);    // attach the cookie to the HTTP response
    }

    /**
     * Deletes the "Remember Me" cookie by setting its max age to zero.
     * Called when the user logs out or unchecks "Remember Me".
     *
     * @param response the HTTP response to attach the deletion cookie to
     */
    public static void clearRememberMe(HttpServletResponse response) {
        Cookie cookie = new Cookie(REMEMBER_ME, ""); // empty value — content doesn't matter
        cookie.setMaxAge(0);   // max age of 0 instructs the browser to delete the cookie immediately
        cookie.setPath("/");   // must match the path used when the cookie was created
        response.addCookie(cookie);
    }

    /**
     * Reads the saved username from the "Remember Me" cookie in the incoming request.
     *
     * @param request the current HTTP request whose cookies should be searched
     * @return the saved username string, or {@code null} if the cookie is not present
     */
    public static String getRememberedUsername(HttpServletRequest request) {
        // Guard: getCookies() returns null when the request has no cookies at all
        if (request.getCookies() == null) return null;

        // Iterate all cookies and return the value of the remember-me cookie if found
        for (Cookie c : request.getCookies()) {
            if (REMEMBER_ME.equals(c.getName())) return c.getValue();
        }
        return null; // remember-me cookie not found
    }
}

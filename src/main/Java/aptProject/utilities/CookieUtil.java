package aptProject.utilities;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CookieUtil - handles the "Remember Me" cookie on the login page.
 */
public final class CookieUtil {

    private static final String REMEMBER_ME = "rememberedUser";
    private static final int    SEVEN_DAYS  = 7 * 24 * 60 * 60; // seconds

    private CookieUtil() {}

    /** Save username in a cookie for 7 days (Remember Me) */
    public static void setRememberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(REMEMBER_ME, username);
        cookie.setMaxAge(SEVEN_DAYS);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // not accessible by JavaScript
        response.addCookie(cookie);
    }

    /** Delete the Remember Me cookie */
    public static void clearRememberMe(HttpServletResponse response) {
        Cookie cookie = new Cookie(REMEMBER_ME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /** Read the saved username from the cookie (returns null if not found) */
    public static String getRememberedUsername(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if (REMEMBER_ME.equals(c.getName())) return c.getValue();
        }
        return null;
    }
}

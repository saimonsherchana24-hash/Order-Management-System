package aptProject.filter;

import aptProject.utilities.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * AuthFilter — centralized authentication and authorization filter.
 *
 * Intercepts every request to protected URL patterns and enforces:
 *   1. Authentication  — user must be logged in (have a valid session)
 *   2. Authorization   — admin-only URLs require the ADMIN role
 *
 * Protected URL patterns (configured via @WebFilter):
 *   /profile/*       — user profile pages
 *   /cart            — shopping cart
 *   /order/*         — checkout and order placement
 *   /tracking        — order tracking
 *   /admin/*         — all admin panel pages
 *
 * Public URLs (not intercepted):
 *   /login, /register, /menu, /about, /contact, /home,
 *   /Resource/*, /css/*, /js/*
 *
 * Redirect behaviour:
 *   - Not logged in  → /login
 *   - Logged in but not ADMIN accessing /admin/* → /menu
 */
@WebFilter(urlPatterns = {
        "/profile",
        "/profile/*",
        "/cart",
        "/order/*",
        "/tracking",
        "/admin/*"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialisation needed
    }

    /**
     * Main filter logic — runs before every matched request.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // Cast to HTTP-specific types to access session and redirect
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String requestURI = request.getRequestURI(); // e.g. /OrderManagementSystem/admin/dashboard

        // ── Check 1: user must be logged in ──────────────────────────────────
        if (!SessionUtil.isLoggedIn(request)) {
            // Not authenticated — redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return; // stop the filter chain; do not forward to the servlet
        }

        // ── Check 2: admin-only URLs require the ADMIN role ───────────────────
        String contextPath = request.getContextPath(); // e.g. /OrderManagementSystem
        if (requestURI.startsWith(contextPath + "/admin/")) {
            if (!SessionUtil.isAdmin(request)) {
                // Logged in but not an admin — redirect to the menu page
                response.sendRedirect(request.getContextPath() + "/menu");
                return; // stop the filter chain
            }
        }

        // ── All checks passed — continue to the target servlet ────────────────
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}

package aptProject.utilities;

/**
 * PasswordUtil - handles password hashing and verification.
 * Currently stores plain text (can be upgraded to BCrypt later).
 */
public final class PasswordUtil {

    private PasswordUtil() {}

    /** Hash a password before saving to DB */
    public static String hashPassword(String password) {
        return password; // plain text for now
    }

    /** Check if entered password matches the stored one */
    public static boolean verifyPassword(String entered, String stored) {
        return entered != null && entered.equals(stored);
    }
}

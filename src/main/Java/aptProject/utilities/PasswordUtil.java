package aptProject.utilities;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * PasswordUtil — password hashing using SHA-256.
 *
 * How it works:
 *   hashPassword("mypassword")  →  SHA-256  →  64-char hex string
 *   e.g. "89e01536ac207279409d4de1e5253e01f4a1769e696db0d6062ca9b8f56767c8"
 *
 * Stored in DB as a plain 64-character hex string.
 * Only uses characters 0-9 and a-f — no encoding issues, no special characters.
 *
 * Verification:
 *   Hash the entered password the same way and compare the two hex strings.
 */
public final class PasswordUtil {

    private PasswordUtil() {}

    /**
     * Hash a password using SHA-256.
     * Returns a 64-character lowercase hex string.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert each byte to 2-digit hex
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString(); // always exactly 64 characters

        } catch (Exception e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    /**
     * Verify a password against its stored SHA-256 hex hash.
     *
     * @param entered  what the user typed in the login form
     * @param stored   the 64-char hex hash from the database
     */
    public static boolean verifyPassword(String entered, String stored) {
        if (entered == null || stored == null) return false;
        return hashPassword(entered).equals(stored);
    }
}

package aptProject.utilities;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * PasswordUtil — utility class for hashing and verifying passwords using SHA-256.
 *
 * <p>Passwords are never stored in plain text. When a user registers or changes
 * their password, the plain-text value is hashed here and only the resulting
 * 64-character hex string is saved to the database.</p>
 *
 * <p>How it works:</p>
 * <pre>
 *   hashPassword("mypassword")  →  SHA-256  →  "89e01536ac207279..."  (64 hex chars)
 * </pre>
 *
 * <p>Verification: hash the entered password the same way and compare the two
 * hex strings with {@link #verifyPassword(String, String)}.</p>
 */
public final class PasswordUtil {

    /** Private constructor — this class should never be instantiated. */
    private PasswordUtil() {}

    /**
     * Hashes a plain-text password using the SHA-256 algorithm.
     *
     * <p>The input is encoded as UTF-8 bytes before hashing, and the resulting
     * byte array is converted to a lowercase hexadecimal string (always exactly
     * 64 characters long).</p>
     *
     * @param password the plain-text password to hash
     * @return a 64-character lowercase hex string representing the SHA-256 hash
     * @throws RuntimeException if the SHA-256 algorithm is unavailable (should never happen on standard JVMs)
     */
    public static String hashPassword(String password) {
        try {
            // Obtain a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password bytes (UTF-8 encoding ensures consistent results across platforms)
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert each byte to a 2-digit lowercase hex string and concatenate
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b)); // %02x = zero-padded 2-char hex
            }
            return hex.toString(); // always exactly 64 characters

        } catch (Exception e) {
            // SHA-256 is guaranteed by the Java spec — this should never be thrown
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    /**
     * Verifies a plain-text password against a stored SHA-256 hex hash.
     *
     * <p>Hashes the entered password and compares it to the stored hash.
     * Returns {@code false} immediately if either argument is {@code null}.</p>
     *
     * @param entered the plain-text password typed by the user in the login form
     * @param stored  the 64-character hex hash retrieved from the database
     * @return {@code true} if the hashes match (password is correct); {@code false} otherwise
     */
    public static boolean verifyPassword(String entered, String stored) {
        // Guard against null inputs — treat as no match
        if (entered == null || stored == null) return false;

        // Hash the entered password and compare to the stored hash
        return hashPassword(entered).equals(stored);
    }
}

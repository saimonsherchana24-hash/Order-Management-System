package aptProject.utilities;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {

    private PasswordUtil() {}

    // ── HASH ─────────────────────────────────────────────────────────────────

    /**
     * Hash a plain-text password.
     * Returns a string in the format:  base64(salt) $ base64(hash)
     * This is what gets saved to the database.
     */
    public static String hashPassword(String plainPassword) {
        try {
            // Step 1: generate a random salt
            byte[] salt = generateSalt();

            // Step 2: hash (salt + password) with SHA-256
            byte[] hash = sha256(salt, plainPassword);

            // Step 3: encode both to Base64 and join with $
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return saltBase64 + "$" + hashBase64;

        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // ── VERIFY ───────────────────────────────────────────────────────────────

    /**
     * Check if an entered plain-text password matches the stored hash.
     *
     * @param enteredPassword  what the user typed in the login form
     * @param storedValue      the salt$hash string from the database
     * @return true if the password is correct
     */
    public static boolean verifyPassword(String enteredPassword, String storedValue) {
        if (enteredPassword == null || storedValue == null) return false;

        // Support plain-text passwords already in the DB (legacy / test accounts)
        if (!storedValue.contains("$")) {
            return enteredPassword.equals(storedValue);
        }

        try {
            // Step 1: split stored value back into salt and hash
            String[] parts    = storedValue.split("\\$", 2);
            byte[]   salt     = Base64.getDecoder().decode(parts[0]);
            byte[]   expected = Base64.getDecoder().decode(parts[1]);

            // Step 2: hash the entered password with the same salt
            byte[] actual = sha256(salt, enteredPassword);

            // Step 3: compare byte-by-byte (constant-time to prevent timing attacks)
            return MessageDigest.isEqual(actual, expected);

        } catch (Exception e) {
            return false;
        }
    }

    // ── HELPERS ──────────────────────────────────────────────────────────────

    /** Generate a random 16-byte salt using a cryptographically secure RNG */
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    /**
     * Run SHA-256 on (salt + password).
     * SHA-256 is a one-way function — you cannot reverse it to get the original password.
     */
    private static byte[] sha256(byte[] salt, String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);                          // feed in the salt first
        digest.update(password.getBytes("UTF-8"));    // then the password
        return digest.digest();                       // returns 32-byte hash
    }
}

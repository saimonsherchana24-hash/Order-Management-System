package aptProject.utilities;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        return password;
    }

    public static boolean verifyPassword(String password, String storedPassword) {
        return password != null && password.equals(storedPassword);
    }
}
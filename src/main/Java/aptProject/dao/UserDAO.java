package aptProject.dao;

import aptProject.dao.Interface.UserDAOInterface;
import aptProject.model.User;
import aptProject.utilities.DBConnection;
import aptProject.utilities.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDAO — Data Access Object for the {@code users} table.
 * <p>Handles all database operations related to user accounts:
 * registration, login authentication, profile updates, and password changes.
 * Implements { UserDAOInterface} to keep the data layer behind a contract.</p>
 */
public class UserDAO implements UserDAOInterface {

    /**
     * Inserts a new user record into the {@code users} table.
     * @param user a { User} object populated with registration data (full name, username, email, hashed password, role)
     * @return {true} if exactly one row was inserted; {@code false} otherwise
     */
    @Override
    public boolean register(User user) {
        // SQL to insert a new user with all required fields
        String sql = "INSERT INTO users (full_name, username, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind each user field to the corresponding placeholder
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPasswordHash()); // already hashed before calling this method
            ps.setString(5, user.getRole());

            // executeUpdate returns the number of rows affected; 1 means success
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Looks up a user by their email address.
     * @param email the email address to search for
     * @return the matching { User}, or { null} if not found
     */
    @Override
    public User findByEmail(String email) {
        // Select all columns needed to reconstruct a User object
        String sql = "SELECT id, full_name, username, email, password_hash, role, profile_image FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email); // bind the email parameter
            try (ResultSet rs = ps.executeQuery()) {
                // If a row exists, map it to a User object and return it
                if (rs.next()) return mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no matching user found
    }

    /**
     * Looks up a user by their username.
     * @param username the username to search for
     * @return the matching {User}, or { null} if not found
     */
    @Override
    public User findByUsername(String username) {
        // Select all columns needed to reconstruct a User object
        String sql = "SELECT id, full_name, username, email, password_hash, role, profile_image FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username); // bind the username parameter
            try (ResultSet rs = ps.executeQuery()) {
                // If a row exists, map it to a User object and return it
                if (rs.next()) return mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no matching user found
    }

    /**
     * Authenticates a user by username or email and verifies their password.
     * <p>First tries to find the user by username; if not found, falls back to email.
     * Then verifies the entered password against the stored SHA-256 hash.</p>
     * @param usernameOrEmail the value typed in the login form (username or email)
     * @param password        the plain-text password entered by the user
     * @return the authenticated { User} if credentials match; { null} otherwise
     */
    @Override
    public User login(String usernameOrEmail, String password) {
        // Try username lookup first, then fall back to email lookup
        User user = findByUsername(usernameOrEmail);
        if (user == null) user = findByEmail(usernameOrEmail);

        if (user != null) {
            // Verify the entered password against the stored hash
            boolean match = PasswordUtil.verifyPassword(password, user.getPasswordHash());

            // Debug log: shows stored hash, entered hash, and match result
            System.out.println("[LOGIN] user=" + usernameOrEmail
                + " | stored=" + user.getPasswordHash()
                + " | entered=" + PasswordUtil.hashPassword(password)
                + " | match=" + match);

            if (match) return user; // credentials are valid — return the user
        }
        return null; // authentication failed
    }

    /**
     * Updates a user's full name and email address.
     * @param userId   the ID of the user to update
     * @param fullName the new full name
     * @param email    the new email address
     * @return {true} if the row was updated successfully; { false} otherwise
     */
    @Override
    public boolean updateProfile(int userId, String fullName, String email) {
        // SQL to update only the name and email fields for a specific user
        String sql = "UPDATE users SET full_name = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setInt(3, userId); // target the correct user by ID

            return ps.executeUpdate() == 1; // 1 row updated = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Replaces a user's stored password hash with a new one.
     * @param userId            the ID of the user whose password is being changed
     * @param newHashedPassword the new SHA-256 hash to store (must be pre-hashed)
     * @return {true} if the row was updated successfully; { false} otherwise
     */
    @Override
    public boolean updatePassword(int userId, String newHashedPassword) {
        // SQL to update only the password_hash column for a specific user
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newHashedPassword); // the new hashed password
            ps.setInt(2, userId);               // target the correct user by ID

            return ps.executeUpdate() == 1; // 1 row updated = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves the file path of a user's uploaded profile image.
     * @param userId    the ID of the user to update
     * @param imagePath the context-relative path to the image (e.g. {@code /Resource/profiles/user_1_123.jpg})
     * @return { true} if the row was updated successfully; { false} otherwise
     */
    @Override
    public boolean updateProfileImage(int userId, String imagePath) {
        // SQL to update only the profile_image column for a specific user
        String sql = "UPDATE users SET profile_image = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, imagePath); // relative URL path to the uploaded image
            ps.setInt(2, userId);       // target the correct user by ID

            return ps.executeUpdate() == 1; // 1 row updated = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Maps the current row of a { ResultSet} to a {User} object.
     * @param rs an open ResultSet positioned on a valid row
     * @return a fully populated {@link User} instance
     * @throws SQLException if any column cannot be read
     */
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));

        // profile_image may not be present in every query — ignore if missing
        try { user.setProfileImage(rs.getString("profile_image")); } catch (SQLException ignored) {}

        return user;
    }
}

package aptProject.dao.Interface;

import aptProject.model.User;

/**
 * UserDAOInterface — contract for all user-related database operations.
 * <p>Any class that handles user persistence (registration, login, profile
 * management) must implement this interface. This keeps the rest of the
 * application decoupled from the concrete DAO implementation.</p>
 */
public interface UserDAOInterface {

    /**
     * Inserts a new user into the database.
     * @param user the { User} object containing registration details
     * @return {true} if the user was created successfully; {false} otherwise
     */
    boolean register(User user);

    /**
     * Finds a user by their email address.
     * @param email the email to search for
     * @return the matching { User}, or { null} if not found
     */
    User findByEmail(String email);

    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return the matching {User}, or { null} if not found
     */
    User findByUsername(String username);

    /**
     * Authenticates a user using their username or email and plain-text password.
     * @param usernameOrEmail the login identifier (username or email)
     * @param password        the plain-text password to verify
     * @return the authenticated {User} if credentials are valid; {null} otherwise
     */
    User login(String usernameOrEmail, String password);

    /**
     * Updates a user's full name and email address.
     * @param userId   the ID of the user to update
     * @param fullName the new full name
     * @param email    the new email address
     * @return {true} if the update succeeded; {false} otherwise
     */
    boolean updateProfile(int userId, String fullName, String email);

    /**
     * Replaces a user's stored password hash with a new hashed password.
     * @param userId            the ID of the user
     * @param newHashedPassword the new SHA-256 hash to store
     * @return {true} if the update succeeded; {false} otherwise
     */
    boolean updatePassword(int userId, String newHashedPassword);

    /**
     * Saves the file path of a user's profile image.
     * @param userId    the ID of the user
     * @param imagePath the context-relative path to the uploaded image
     * @return {true} if the update succeeded; {false} otherwise
     */
    boolean updateProfileImage(int userId, String imagePath);
}

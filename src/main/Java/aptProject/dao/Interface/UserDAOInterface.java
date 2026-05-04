package aptProject.dao.Interface;

import aptProject.model.User;

public interface UserDAOInterface {
    boolean register(User user);

    User findByEmail(String email);

    User findByUsername(String username);

    User login(String usernameOrEmail, String password);

    /** Update full_name and email for a user */
    boolean updateProfile(int userId, String fullName, String email);

    /** Update password_hash for a user */
    boolean updatePassword(int userId, String newHashedPassword);
}
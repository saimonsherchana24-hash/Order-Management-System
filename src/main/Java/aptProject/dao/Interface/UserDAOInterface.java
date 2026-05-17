package aptProject.dao.Interface;

import aptProject.model.User;

public interface UserDAOInterface {

    boolean register(User user);

    User findByEmail(String email);

    User findByUsername(String username);

    User login(String usernameOrEmail, String password);

    boolean updateProfile(int userId, String fullName, String email);

    boolean updatePassword(int userId, String newHashedPassword);

    boolean updateProfileImage(int userId, String imagePath);
}

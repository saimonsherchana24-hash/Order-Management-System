package aptProject.dao.Interface;

import aptProject.model.User;

public interface UserDAOInterface {
    boolean register(User user);

    User findByEmail(String email);

    User findByUsername(String username);

    User login(String usernameOrEmail, String password);
}
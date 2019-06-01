package education.service;

import education.entity.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    int insert(User user);
    User findUser(String username, String password);
    List<User> findAllUsers();
}

package education.service;

import education.dao.UserMapper;
import education.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;


    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int insert(User user) {
        int result = userMapper.insert(user);


        return result;
    }

    @Override
    public User findUser(String username, String password) {
        return userMapper.findUser(username, password);
    }

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }
}

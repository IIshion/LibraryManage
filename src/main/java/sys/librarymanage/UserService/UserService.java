package sys.librarymanage.UserService;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import sys.librarymanage.Entity.User;
import sys.librarymanage.UserRepository.UserRepository;
import sys.librarymanage.Utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 查询指定用户
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUsername(userName);
    }

    // 注册用户
    public void registerUser(String userName, String password) {
        if (getUserByUserName(userName) != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); // 加密
        userRepository.save(user);
    }

    // 用户登录
    public String userLogin(String userName, String password){
        if (!userRepository.existsUserByUsername(userName)) {
            throw new RuntimeException("User not exist");
        }
        User user = getUserByUserName(userName);

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("Wrong Password");
        }
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", userName);
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        return JwtUtils.generateToken(claims, userName);
    }

    // 删除用户
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    // 修改密码
    public void changePassword(String userName, String oldPassword, String newPassword) {
         User u = getUserByUserName(userName);
         if (!BCrypt.checkpw(oldPassword, u.getPassword())) {
             throw new RuntimeException("密码错误");
         } else {
             u.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
             userRepository.save(u);
         }
    }

}

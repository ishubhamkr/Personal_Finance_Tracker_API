package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.model.UserModel;
import personal.finance.tracker.repository.UserRepository;
import personal.finance.tracker.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;



@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public UserModel register(User user) {
        //Check if email or username already exists
        if (!ObjectUtils.isEmpty(userRepository.findByUserName(user.getUserName()))) {
            throw new IllegalArgumentException("Username already exists!");
        }

        if (!ObjectUtils.isEmpty(userRepository.findByEmail(user.getEmail()))) {
            throw new IllegalArgumentException("Email already exists!");
        }
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDt(LocalDateTime.now());
        user.setUpdatedDt(LocalDateTime.now());
        userRepository.save(user);
        UserModel userModel = new UserModel();
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setAddress(user.getAddress());
        userModel.setUserName(user.getUserName());
        return userModel;
    }

    @Override
    public String login(String userName, String password) {
        User user = userRepository.findByUserName(userName);

        if (!ObjectUtils.isEmpty(user) && passwordEncoder.matches(password, user.getPassword()))
        {
            return jwtUtil.generateToken(user.getUserName(),"user");
        } else {
            throw new IllegalArgumentException("Invalid userName or password!");
        }
    }
}

package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.repository.UserRepository;
import personal.finance.tracker.util.JwtUtil;

import java.time.LocalDateTime;




@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    @Transactional
    public String register(User user) {
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
        return jwtUtil.generateToken(user);
    }

    @Override
    public String login(String userName, String password) {
        // Fetch user by username
        User user = userRepository.findByUserName(userName);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Generate token if credentials are valid
        return jwtUtil.generateToken(user);
    }

}

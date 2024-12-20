package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.repository.UserRepository;

import java.util.Optional;



@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User register(User user) {
        //Check if email or username already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Email or Username already exists!");
        }

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new IllegalArgumentException("Invalid email or password!");
        }
    }
}

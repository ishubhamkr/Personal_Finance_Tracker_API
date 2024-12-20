package personal.finance.tracker.service;

import personal.finance.tracker.entity.User;

public interface AuthService {
    User register(User user);

    User login(String email, String password);
}

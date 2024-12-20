package personal.finance.tracker.service;

import personal.finance.tracker.entity.User;
import personal.finance.tracker.model.UserModel;

public interface AuthService {
    UserModel register(User user);

    UserModel login(String email, String password);
}

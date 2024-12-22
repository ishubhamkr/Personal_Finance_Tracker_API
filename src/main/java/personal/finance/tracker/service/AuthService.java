package personal.finance.tracker.service;

import personal.finance.tracker.entity.User;
import personal.finance.tracker.model.UserModel;

public interface AuthService {
    String register(User user);

    String login(String userName, String password);
}

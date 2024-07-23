package ru.realty.erealty.service.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.realty.erealty.entity.User;

import java.util.Optional;

public interface UserModificationService {

    void deleteById(Integer userId);

    Optional<User> saveUser(User user, String url);

    void saveUser(
            User user,
            HttpSession httpSession,
            HttpServletRequest httpServletRequest
    );

    void removeSessionMessage();

    void resetPasswordProcess(User user);
}

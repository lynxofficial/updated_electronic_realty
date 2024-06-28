package ru.realty.erealty.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.realty.erealty.entity.User;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByEmail(String email);

    void deleteById(Integer userId);

    User saveUser(User user, String url);

    void saveUser(User user, HttpSession httpSession, HttpServletRequest httpServletRequest);

    void removeSessionMessage();

    void sendEmail(User user, String url);

    Boolean verifyAccount(String verificationCode);

    String sendEmail(User user);

    boolean hasExpired(LocalDateTime expiryDateTime);

    void generateDigitalSignature(String passwordForDigitalSignature, User user) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException;

    void resetPasswordProcess(User user);
}

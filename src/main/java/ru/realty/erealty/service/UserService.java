package ru.realty.erealty.service;

import ru.realty.erealty.entity.User;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;

public interface UserService {

    User saveUser(User user, String url);

    void removeSessionMessage();

    void sendEmail(User user, String url);

    Boolean verifyAccount(String verificationCode);

    String sendEmail(User user);

    boolean hasExpired(LocalDateTime expiryDateTime);

    String generateDigitalSignature(String passwordForDigitalSignature) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException;

    void deleteById(Integer userId);
}

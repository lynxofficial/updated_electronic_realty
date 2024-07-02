package ru.realty.erealty.service;

import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.time.LocalDateTime;

public interface UserVerificationService {

    void sendEmail(User user, String url);

    Boolean verifyAccount(String verificationCode);

    String sendEmail(User user);

    boolean hasExpired(LocalDateTime expiryDateTime);

    Boolean isNotPositiveBalanceOrExistsDigitalSignature(User currentUser, User targetUser, RealtyObject realtyObject);
}

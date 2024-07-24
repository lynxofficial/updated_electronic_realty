package ru.realty.erealty.service.user;

import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.time.LocalDateTime;

public interface UserVerificationService {

    Boolean verifyAccount(String verificationCode);

    boolean hasExpired(LocalDateTime expiryDateTime);

    Boolean isNotPositiveBalanceOrExistsDigitalSignature(
            User currentUser,
            User targetUser,
            RealtyObject realtyObject);
}

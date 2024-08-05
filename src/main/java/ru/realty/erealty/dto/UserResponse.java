package ru.realty.erealty.dto;

import lombok.Builder;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.RealtyObject;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record UserResponse(Integer id,
                           String fullName,
                           String email,
                           String password,
                           BigDecimal balance,
                           String passwordForDigitalSignature,
                           String digitalSignature,
                           String role,
                           Boolean enable,
                           String verificationCode,
                           PasswordResetToken passwordResetToken,
                           List<RealtyObject> realtyObjects) {
}

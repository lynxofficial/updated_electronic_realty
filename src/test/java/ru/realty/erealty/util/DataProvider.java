package ru.realty.erealty.util;

import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DataProvider {
    public static Agency.AgencyBuilder agencyBuilder() {
        return Agency.builder()
                .agencyId(1)
                .address("Test district, 0")
                .agencyName("Test Agency");
    }

    public static PasswordResetToken.PasswordResetTokenBuilder passwordResetTokenBuilder() {
        return PasswordResetToken.builder()
                .token("123456")
                .user(userBuilder().build())
                .id(0)
                .expiryDateTime(LocalDateTime.now());
    }

    public static RealtyObject.RealtyObjectBuilder realtyObjectBuilder() {
        return RealtyObject.builder()
                .realtyObjectId(0)
                .realtyObjectName("TestRealtyObject")
                .price(BigDecimal.valueOf(1_000_000L))
                .address("Test district, 10")
                .description("Test description")
                .square(40.9)
                .imageUrl("realtyObjectImageForTest.png")
                .user(userBuilder().build());
    }

    public static User.UserBuilder userBuilder() {
        return User.builder()
                .userId(0)
                .password("1234")
                .email("test@test.com")
                .role("ROLE_USER")
                .balance(BigDecimal.ZERO)
                .enable(false)
                .fullName("Test Test Test")
                .passwordForDigitalSignature("123")
                .digitalSignature("DigitalSignature");
    }
}

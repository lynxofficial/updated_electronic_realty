package ru.realty.erealty.util;

import lombok.Builder;
import ru.realty.erealty.entity.User;

import java.math.BigDecimal;

public class DataProvider {
    public static User.UserBuilder generateUserData() {
        return User.builder()
                .userId(0)
                .balance(BigDecimal.ZERO)
                .digitalSignature("123456")
                .email("test@test.com")
                .role("ROLE_USER")
                .enable(false)
                .fullName("Test Test Test")
                .passwordForDigitalSignature("12345")
                .password("test");
    }
}

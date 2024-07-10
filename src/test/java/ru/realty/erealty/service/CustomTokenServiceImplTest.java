package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.entity.PasswordResetToken;

class CustomTokenServiceImplTest extends BaseSpringBootTest {
    @Test
    void findByTokenShouldWork() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken("123");
        Mockito.when(customTokenRepository.findByToken("123")).thenReturn(passwordResetToken);
        Assertions.assertEquals(passwordResetToken, customTokenServiceImpl.findByToken("123"));
    }
}

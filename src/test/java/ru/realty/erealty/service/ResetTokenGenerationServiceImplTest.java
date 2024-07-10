package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;

class ResetTokenGenerationServiceImplTest extends BaseSpringBootTest {
    @Test
    void generateResetTokenShouldWork() {
        User user = new User();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        Mockito.when(customTokenRepository.save(passwordResetToken))
                .thenReturn(passwordResetToken);
        resetTokenGenerationServiceImpl.generateResetToken(user);
        Assertions.assertNull(passwordResetToken.getToken());
    }
}

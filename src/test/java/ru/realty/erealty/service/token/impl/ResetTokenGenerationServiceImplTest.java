package ru.realty.erealty.service.token.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

class ResetTokenGenerationServiceImplTest extends BaseSpringBootTest {
    @Test
    void generateResetTokenShouldWork() {
        User user = DataProvider.userBuilder().build();
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .user(user)
                .token(null)
                .build();
        Mockito.when(customTokenRepository.save(passwordResetToken))
                .thenReturn(passwordResetToken);

        resetTokenGenerationServiceImpl.generateResetToken(user);

        Assertions.assertThat(passwordResetToken.getToken())
                .isNull();
        Assertions.assertThat(passwordResetToken.getUser())
                .isNotNull();
    }

    @Test
    void generateResetTokenShouldNotWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .user(null)
                .build();
        Mockito.when(customTokenRepository.save(passwordResetToken))
                .thenReturn(passwordResetToken);

        resetTokenGenerationServiceImpl.generateResetToken(null);

        Assertions.assertThat(passwordResetToken.getUser())
                .isNull();
    }
}

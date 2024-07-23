package ru.realty.erealty.service.token.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ResetTokenGenerationServiceImplTest extends BaseSpringBootTest {
    @Test
    void generateResetTokenShouldWork() {
        User user = DataProvider.userBuilder().build();
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .user(user)
                .token(null)
                .build();
        when(customTokenRepository.save(passwordResetToken))
                .thenReturn(passwordResetToken);

        resetTokenGenerationServiceImpl.generateResetToken(user);

        assertThat(passwordResetToken.getToken())
                .isNull();
        assertThat(passwordResetToken.getUser())
                .isNotNull();
    }

    @Test
    void generateResetTokenShouldNotWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .user(null)
                .build();
        when(customTokenRepository.save(passwordResetToken))
                .thenReturn(passwordResetToken);

        resetTokenGenerationServiceImpl.generateResetToken(null);

        assertThat(passwordResetToken.getUser())
                .isNull();
    }
}

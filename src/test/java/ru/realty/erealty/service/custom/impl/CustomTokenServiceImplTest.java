package ru.realty.erealty.service.custom.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.util.DataProvider;

class CustomTokenServiceImplTest extends BaseSpringBootTest {
    @Test
    void findByTokenShouldWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .token("123").build();
        Mockito.when(customTokenRepository.findByToken("123")).thenReturn(passwordResetToken);

        Assertions.assertThat(customTokenServiceImpl.findByToken("123"))
                .isEqualTo(passwordResetToken);
    }

    @Test
    void findByTokenShouldNotWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .token("123").build();
        Mockito.when(customTokenRepository.findByToken("12345"))
                .thenReturn(passwordResetToken);

        Assertions.assertThat(customTokenServiceImpl.findByToken("123"))
                .isNotEqualTo(passwordResetToken);
    }
}

package ru.realty.erealty.service.custom.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.util.DataProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CustomTokenServiceImplTest extends BaseSpringBootTest {
    @Test
    void findByTokenShouldWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .token("123").build();
        when(customTokenRepository.findByToken("123")).thenReturn(passwordResetToken);

        assertThat(customTokenServiceImpl.findByToken("123"))
                .isEqualTo(passwordResetToken);
    }

    @Test
    void findByTokenShouldNotWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .token("123").build();
        when(customTokenRepository.findByToken("12345"))
                .thenReturn(passwordResetToken);

        assertThat(customTokenServiceImpl.findByToken("123"))
                .isNotEqualTo(passwordResetToken);
    }
}

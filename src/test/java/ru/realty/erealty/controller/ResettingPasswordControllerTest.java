package ru.realty.erealty.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class ResettingPasswordControllerTest extends BaseSpringBootTest {
    @BeforeEach
    public void verifyNoInteractionsWithMockBeans() {
        verifyNoInteractions(userRepository, customTokenRepository);
    }

    @Test
    void forgotPasswordShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/forgotPassword",
                200
        );
    }

    @Test
    void forgotPasswordShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/forgotPassword/1",
                404
        );
    }

    @Test
    void forgotPasswordProcessShouldWorkWithNotNullTargetUser() {
        User user = DataProvider.userBuilder()
                .build();

        when(userRepository.findByEmail(null))
                .thenReturn(Optional.ofNullable(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/forgotPassword",
                302
        );

        verify(userRepository, times(2)).findByEmail(null);
    }

    @Test
    void forgotPasswordProcessShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/forgotPassword/10",
                404
        );
    }

    @Test
    void resetPasswordFormShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/1",
                302
        );
    }

    @Test
    void resetPasswordFormShouldWorkWithPasswordResetToken() {
        PasswordResetToken passwordResetToken = DataProvider
                .createPasswordResetTokenWithExpiryDateTimePlusMinutes(0, 45L);

        when(customTokenRepository.findByToken(passwordResetToken.getToken()))
                .thenReturn(passwordResetToken);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/123456",
                200
        );

        verify(customTokenRepository).findByToken(passwordResetToken.getToken());
    }

    @Test
    void resetPasswordFormShouldNotWorkWithNullPasswordResetToken() {
        when(customTokenRepository.findByToken("123456"))
                .thenReturn(null);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/123456",
                302
        );

        verify(customTokenRepository).findByToken("123456");
    }

    @Test
    void resetPasswordFormShouldNotWorkWithExpiredDateTime() {
        PasswordResetToken passwordResetToken = DataProvider
                .createPasswordResetTokenWithExpiryDateTimeMinutesMinutes(0, 30L);

        when(customTokenRepository.findByToken("123456"))
                .thenReturn(passwordResetToken);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/123456",
                302
        );

        verify(customTokenRepository).findByToken("123456");
    }

    @Test
    void resetPasswordFormShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword",
                405
        );
    }

    @Test
    void resetPasswordProcessShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/resetPassword",
                302
        );
    }

    @Test
    void resetPasswordProcessShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/resetPassword/10",
                405
        );
    }
}

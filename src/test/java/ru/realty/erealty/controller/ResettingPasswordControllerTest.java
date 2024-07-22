package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

public class ResettingPasswordControllerTest extends BaseSpringBootTest {
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
        Mockito.when(userRepository.findByEmail(null))
                .thenReturn(Optional.ofNullable(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/forgotPassword",
                302
        );
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
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .expiryDateTime(LocalDateTime.now().plusMinutes(45))
                .build();
        Mockito.when(customTokenRepository.findByToken(passwordResetToken.getToken()))
                .thenReturn(passwordResetToken);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/123456",
                200
        );
    }

    @Test
    void resetPasswordFormShouldNotWorkWithNullPasswordResetToken() {
        Mockito.when(customTokenRepository.findByToken("123456"))
                .thenReturn(null);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/123456",
                302
        );
    }

    @Test
    void resetPasswordFormShouldNotWorkWithExpiredDateTime() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .expiryDateTime(LocalDateTime.now().minusMinutes(30))
                .build();
        Mockito.when(customTokenRepository.findByToken("123456"))
                .thenReturn(passwordResetToken);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/resetPassword/123456",
                302
        );
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

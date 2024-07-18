package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.time.LocalDateTime;

public class ResettingPasswordControllerTest extends BaseSpringBootTest {
    @Test
    void forgotPasswordShouldWork() {
        webTestClient.get()
                .uri("/forgotPassword")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void forgotPasswordShouldNotWork() {
        webTestClient.get()
                .uri("/forgotPassword/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void forgotPasswordProcessShouldWork() {
        webTestClient.post()
                .uri("/forgotPassword")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void forgotPasswordProcessShouldNotWork() {
        webTestClient.post()
                .uri("/forgotPassword/10")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void resetPasswordFormShouldWork() {
        webTestClient.get()
                .uri("/resetPassword/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void resetPasswordFormShouldWorkWithPasswordResetToken() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .expiryDateTime(LocalDateTime.now().plusMinutes(45))
                .build();
        Mockito.when(customTokenRepository.findByToken(passwordResetToken.getToken()))
                .thenReturn(passwordResetToken);

        webTestClient.get()
                .uri("/resetPassword/123456")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody();
    }

    @Test
    void resetPasswordShouldNotWork() {
        webTestClient.get()
                .uri("/resetPassword")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void resetPasswordProcessShouldWork() {
        webTestClient.post()
                .uri("/resetPassword")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void resetPasswordProcessShouldNotWork() {
        webTestClient.post()
                .uri("/resetPassword/10")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }
}

package ru.realty.erealty.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;

public class RegistrationControllerTest extends BaseSpringBootTest {
    @Test
    void saveUserThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.post()
                        .uri("/saveUser")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is3xxRedirection()
                        .expectBody());
    }

    @Test
    void verifyAccountShouldWork() {
        webTestClient.get()
                .uri("/verify")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void verifyAccountShouldNotWork() {
        webTestClient.get()
                .uri("/verifyAccount")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }
}

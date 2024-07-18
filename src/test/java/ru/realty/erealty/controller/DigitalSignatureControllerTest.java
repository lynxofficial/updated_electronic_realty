package ru.realty.erealty.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;

class DigitalSignatureControllerTest extends BaseSpringBootTest {
    @Test
    void getGenerateUserDigitalSignatureShouldWork() throws Exception {
        webTestClient.get()
                .uri("/generateUserDigitalSignature")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void getGenerateUserDigitalSignatureShouldNotWork() throws Exception {
        webTestClient.get()
                .uri("/generateUserDigitalSignature/10")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void generateUserDigitalSignatureThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.post()
                        .uri("/generateUserDigitalSignature")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is3xxRedirection()
                        .expectBody());
    }
}

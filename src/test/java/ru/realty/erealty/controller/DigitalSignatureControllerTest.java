package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DigitalSignatureControllerTest extends BaseSpringBootTest {
    @Test
    void getGenerateUserDigitalSignatureShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/generateUserDigitalSignature",
                200
        );
    }

    @Test
    void getGenerateUserDigitalSignatureShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/generateUserDigitalSignature/10",
                404
        );
    }

    @Test
    void generateUserDigitalSignatureThrowsException() {
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/generateUserDigitalSignature",
                        302
                ));
    }
}

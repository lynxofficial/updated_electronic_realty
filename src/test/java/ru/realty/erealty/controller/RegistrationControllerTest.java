package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RegistrationControllerTest extends BaseSpringBootTest {
    @Test
    void saveUserThrowsException() {
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/saveUser",
                        302
                ));
    }

    @Test
    void verifyAccountShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/verify",
                302
        );
    }

    @Test
    void verifyAccountShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/verifyAccount",
                404
        );
    }
}

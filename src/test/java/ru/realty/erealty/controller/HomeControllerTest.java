package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

class HomeControllerTest extends BaseSpringBootTest {
    @Test
    void indexShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/",
                200
        );
    }

    @Test
    void indexShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/123",
                404
        );
    }

    @Test
    void registerShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/register",
                200
        );
    }

    @Test
    void registerShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/registerUser",
                404
        );
    }

    @Test
    void loginShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/signIn",
                200
        );
    }

    @Test
    void loginShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/login",
                404
        );
    }
}

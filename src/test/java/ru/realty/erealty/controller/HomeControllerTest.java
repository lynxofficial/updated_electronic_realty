package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.realty.erealty.support.BaseSpringBootTest;

class HomeControllerTest extends BaseSpringBootTest {
    @Test
    void indexShouldWork() {
        webTestClient.get()
                .uri("/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void indexShouldNotWork() {
        webTestClient.get()
                .uri("/123")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void registerShouldWork() {
        webTestClient.get()
                .uri("/register")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void registerShouldNotWork() {
        webTestClient.get()
                .uri("/registerUser")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void loginShouldWork() {
        webTestClient.get()
                .uri("/signIn")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void loginShouldNotWork() {
        webTestClient.get()
                .uri("/login")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }
}

package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.support.container.BaseSpringBootTestContainer;

public class UserControllerTest extends BaseSpringBootTest implements BaseSpringBootTestContainer {
    @Test
    void profileShouldWork() {
        webTestClient.get()
                .uri("/user/profile")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void profileShouldNotWork() {
        webTestClient.get()
                .uri("/profile")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void deleteUsersShouldWork() {
        webTestClient.get()
                .uri("/deleteUsers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void deleteUsersShouldNotWork() {
        webTestClient.get()
                .uri("/deleteUsers/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void deleteUserShouldNotWork() {
        webTestClient.post()
                .uri("/deleteUser")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }
}

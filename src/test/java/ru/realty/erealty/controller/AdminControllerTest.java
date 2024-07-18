package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.realty.erealty.support.BaseSpringBootTest;

class AdminControllerTest extends BaseSpringBootTest {
    @Test
    void profileShouldWork() {
        webTestClient.get()
                .uri("/admin/profile")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void profileShouldNotWork() {
        webTestClient.get()
                .uri("/admin/profile/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }
}

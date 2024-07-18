package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.realty.erealty.support.BaseSpringBootTest;

class AgencyControllerTest extends BaseSpringBootTest {
    @Test
    void getAllAgenciesShouldWork() {
        webTestClient.get()
                .uri("/agencies")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void getAllAgenciesShouldNotWork() {
        webTestClient.get()
                .uri("/agencies/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }
}

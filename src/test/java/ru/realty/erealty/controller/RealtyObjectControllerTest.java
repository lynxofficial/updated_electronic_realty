package ru.realty.erealty.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;

class RealtyObjectControllerTest extends BaseSpringBootTest {
    @Test
    void getRealtyObjectShouldWork() {
        webTestClient.get()
                .uri("/realtyObject")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void getRealtyObjectShouldNotWork() {
        webTestClient.get()
                .uri("/realtyObject/4")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void getRealtyObjectsShouldWork() {
        webTestClient.get()
                .uri("/myRealtyObjects")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void getRealtyObjectsShouldNotWork() {
        webTestClient.get()
                .uri("/myRealtyObjects/54")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void sellRealtyThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.post()
                        .uri("/sellRealtyObject")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is3xxRedirection()
                        .expectBody());
    }

    @Test
    void buyRealtyObjectWithIdThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.get()
                        .uri("/buyRealtyObject/10")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is3xxRedirection()
                        .expectBody());
    }

    @Test
    void buyRealtyObjectThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.get()
                        .uri("/buyRealtyObject/buy")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is4xxClientError()
                        .expectBody());
    }

    @Test
    void buyRealtyObjectWithDigitalSignatureThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.post()
                        .uri("/buyRealtyObject")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is3xxRedirection()
                        .expectBody());
    }

    @Test
    void deleteRealtyObjectsShouldWork() {
        webTestClient.get()
                .uri("/deleteRealtyObjects")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody();
    }

    @Test
    void deleteRealtyObjectsShouldNotWork() {
        webTestClient.get()
                .uri("/deleteRealtyObjects/10")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    void deleteRealtyObjectThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> webTestClient.post()
                        .uri("/deleteRealtyObject")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .exchange()
                        .expectStatus()
                        .is3xxRedirection()
                        .expectBody());
    }
}

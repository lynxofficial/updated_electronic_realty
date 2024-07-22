package ru.realty.erealty.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class WebTestClientRequestGenerator {
    public static void generateWebTestClientRequest(
            final WebTestClient webTestClient,
            final HttpMethod httpMethod,
            final String uri,
            final Integer status
    ) {
        webTestClient
                .method(httpMethod)
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(status)
                .expectBody();
    }
}

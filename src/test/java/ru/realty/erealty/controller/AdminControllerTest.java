package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

class AdminControllerTest extends BaseSpringBootTest {
    @Test
    void profileShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/admin/profile",
                302
        );
    }

    @Test
    void profileShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/admin/profile/1",
                302
        );
    }
}

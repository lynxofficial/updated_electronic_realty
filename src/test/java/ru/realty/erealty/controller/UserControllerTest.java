package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import java.math.BigDecimal;
import java.util.Optional;

public class UserControllerTest extends BaseSpringBootTest {
    @Test
    @WithMockUser(username = "test@test.com")
    void profileShouldWork() {
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.ofNullable(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/user/profile",
                200
        );
    }

    @Test
    void profileShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/profile",
                404
        );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void deleteUsersShouldWork() {
        User user = DataProvider.userBuilder()
                .id(1)
                .role("ROLE_ADMIN")
                .balance(BigDecimal.valueOf(100_000L))
                .build();
        Mockito.when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/deleteUsers",
                200
        );
    }

    @Test
    void deleteUsersShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/deleteUsers/1",
                404
        );
    }

    @Test
    void deleteUserShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/deleteUser",
                400
        );
    }
}

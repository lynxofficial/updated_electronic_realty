package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.WebTestClientRequestGenerator;
import ru.realty.erealty.constant.UserRole;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest extends BaseSpringBootTest {
    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void profileShouldWork() {
        User user = DataProvider.userBuilder().build();

        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
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
        User user = DataProvider.createUserWithBalanceAndRole(1, UserRole.ADMIN_ROLE, BigDecimal.valueOf(100_000L));

        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/deleteUsers",
                200
        );

        verify(userRepository).findByEmail("admin@test.com");
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

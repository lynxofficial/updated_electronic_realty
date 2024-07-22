package ru.realty.erealty.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

class RealtyObjectControllerTest extends BaseSpringBootTest {
    @Test
    @WithMockUser(username = "test@test.com")
    void getRealtyObjectShouldWork() {
        User user = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(100_000L))
                .build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/realtyObject",
                200
        );
    }

    @Test
    void getRealtyObjectRedirectionShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/realtyObject",
                302
        );
    }

    @Test
    void getRealtyObjectShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/realtyObject/4",
                404
        );
    }

    @Test
    void getRealtyObjectsRedirectionShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/myRealtyObjects",
                302
        );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void getRealtyObjectsShouldWorkWithAuthenticatedUser() {
        User user = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(100_000L))
                .build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/myRealtyObjects",
                200
        );
    }

    @Test
    void getRealtyObjectsShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/myRealtyObjects/54",
                404
        );
    }

    @Test
    void sellRealtyObjectThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/sellRealtyObject",
                        302
                ));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void buyRealtyObjectShouldWork() {
        User user = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(100_000L))
                .realtyObjects(new ArrayList<>())
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(user)
                .build();
        user.getRealtyObjects().add(realtyObject);
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        Mockito.when(realtyObjectRepository.findById(1))
                .thenReturn(Optional.ofNullable(realtyObject));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/buyRealtyObject/1",
                200
        );
    }

    @Test
    void buyRealtyObjectWithIdThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.GET,
                        "/buyRealtyObject/10",
                        302
                ));
    }

    @Test
    void buyRealtyObjectThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() ->
                        WebTestClientRequestGenerator.generateWebTestClientRequest(
                                webTestClient,
                                HttpMethod.GET,
                                "/buyRealtyObject/buy",
                                404
                        ));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void buyRealtyObjectWithDigitalSignatureShouldWork() {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(100_000L))
                .realtyObjects(new ArrayList<>())
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .id(0)
                .user(targetUser)
                .price(BigDecimal.valueOf(100_000L))
                .build();
        targetUser.getRealtyObjects().add(realtyObject);
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User currentUser = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(100_000L))
                .build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(currentUser));
        Mockito.when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        Mockito.when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/buyRealtyObject/0",
                302
        );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void buyRealtyObjectWithDigitalSignatureShouldNotWorkWithCurrentUserBalanceLessThanOne() {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(100_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .id(0)
                .user(targetUser)
                .build();
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User currentUser = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(currentUser));
        Mockito.when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        Mockito.when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/buyRealtyObject/0",
                200
        );
    }

    @Test
    void buyRealtyObjectWithDigitalSignatureThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/buyRealtyObject/0",
                        302
                ));
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void deleteRealtyObjectsShouldWork() {
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
                "/deleteRealtyObjects",
                200
        );
    }

    @Test
    void deleteRealtyObjectsRedirectionShouldWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/deleteRealtyObjects",
                302
        );
    }

    @Test
    void deleteRealtyObjectsShouldNotWork() {
        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/deleteRealtyObjects/10",
                404
        );
    }

    @Test
    void deleteRealtyObjectThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/deleteRealtyObject",
                        302
                ));
    }
}

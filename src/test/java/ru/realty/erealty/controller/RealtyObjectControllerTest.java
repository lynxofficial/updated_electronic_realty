package ru.realty.erealty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.constant.UserRole;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.WebTestClientRequestGenerator;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RealtyObjectControllerTest extends BaseSpringBootTest {
    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void getRealtyObjectShouldWork() {
        User user = DataProvider.createUserWithBalance(0);

        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/realtyObject",
                200
        );

        verify(userRepository).findByEmail(UserEmail.DEFAULT_EMAIL);
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
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void getRealtyObjectsShouldWorkWithAuthenticatedUser() {
        User user = DataProvider.createUserWithBalance(0);

        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/myRealtyObjects",
                200
        );

        verify(userRepository, times(2)).findByEmail(UserEmail.DEFAULT_EMAIL);
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
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/sellRealtyObject",
                        302
                ));
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectShouldWork() {
        User user = DataProvider.createUserWithBalanceAndRealtyObjects(1);
        RealtyObject realtyObject = DataProvider.createRealtyObjectWithUser(0, user);
        user.getRealtyObjects().add(realtyObject);

        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));
        when(realtyObjectRepository.findById(1))
                .thenReturn(Optional.ofNullable(realtyObject));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/buyRealtyObject/1",
                200
        );

        verify(userRepository).findByEmail(UserEmail.DEFAULT_EMAIL);
        verify(realtyObjectRepository).findById(1);
    }

    @Test
    void buyRealtyObjectWithIdThrowsException() {
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.GET,
                        "/buyRealtyObject/10",
                        302
                ));
    }

    @Test
    void buyRealtyObjectThrowsException() {
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() ->
                        WebTestClientRequestGenerator.generateWebTestClientRequest(
                                webTestClient,
                                HttpMethod.GET,
                                "/buyRealtyObject/buy",
                                404
                        ));
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectWithDigitalSignatureShouldWork() {
        User targetUser = DataProvider.createUserWithBalanceAndRealtyObjects(0);
        RealtyObject realtyObject = DataProvider.createRealtyObjectWithUserAndPrice(
                0,
                targetUser,
                BigDecimal.valueOf(100_000L)
        );
        targetUser.getRealtyObjects().add(realtyObject);
        User currentUser = DataProvider.createUserWithBalance(1);

        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/buyRealtyObject/0",
                302
        );

        verify(realtyObjectRepository).findById(realtyObject.getId());
        verify(userRepository).findById(targetUser.getId());
        verify(userRepository, times(2)).findByEmail(UserEmail.DEFAULT_EMAIL);
        verify(userRepository).save(currentUser);
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectWithDigitalSignatureShouldNotWorkWithCurrentUserBalanceLessThanOne() {
        User targetUser = DataProvider.createUserWithBalance(0);
        RealtyObject realtyObject = DataProvider.createRealtyObjectWithUser(0, targetUser);
        User currentUser = DataProvider.userBuilder().build();

        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.POST,
                "/buyRealtyObject/0",
                200
        );

        verify(realtyObjectRepository).findById(realtyObject.getId());
        verify(userRepository).findById(realtyObject.getUser().getId());
        verify(userRepository, times(2)).findByEmail(UserEmail.DEFAULT_EMAIL);
    }

    @Test
    void buyRealtyObjectWithDigitalSignatureThrowsException() {
        assertThatExceptionOfType(WebClientRequestException.class)
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
        User user = DataProvider.createUserWithBalanceAndRole(1, UserRole.ADMIN_ROLE, BigDecimal.valueOf(100_000L));

        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(user));

        WebTestClientRequestGenerator.generateWebTestClientRequest(
                webTestClient,
                HttpMethod.GET,
                "/deleteRealtyObjects",
                200
        );

        verify(userRepository).findByEmail("admin@test.com");
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
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> WebTestClientRequestGenerator.generateWebTestClientRequest(
                        webTestClient,
                        HttpMethod.POST,
                        "/deleteRealtyObject",
                        302
                ));
    }
}

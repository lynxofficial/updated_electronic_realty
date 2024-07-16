package ru.realty.erealty.service.mail.impl;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.time.Duration;
import java.util.Optional;

class MailSendingServiceImplTest extends BaseSpringBootTest {
    @Test
    void sendEmailWithUrlShouldWork() {
        User user = DataProvider.userBuilder()
                .fullName("Test test test")
                .verificationCode("12345")
                .build();
        String url = "test.com";

        Awaitility.await()
                .atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> mailSendingServiceImpl.sendEmail(user, url));
    }

    @Test
    void sendEmailWithUrlThrowsException() {
        User user = DataProvider.userBuilder()
                .fullName("Test test test")
                .verificationCode("12345")
                .build();
        String url = "test.com";

        Assertions.assertThrows(ConditionTimeoutException.class, () -> Awaitility.await()
                .atMost(Duration.ofMillis(500L))
                .untilAsserted(() -> mailSendingServiceImpl.sendEmail(user, url)));
    }

    @Test
    void sendEmailWithCurrentUserForResettingPasswordShouldWork() {
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        User currentUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Awaitility.await()
                .atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> mailSendingServiceImpl.sendEmail(currentUser));
    }

    @Test
    void sendEmailWithCurrentUserForResettingPasswordThrowsException() {
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(RuntimeException.class, () -> Awaitility.await()
                .atMost(Duration.ofSeconds(1L))
                .untilAsserted(() -> mailSendingServiceImpl.sendEmail(null)));
    }
}

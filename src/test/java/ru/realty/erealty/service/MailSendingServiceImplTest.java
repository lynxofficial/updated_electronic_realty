package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.realty.erealty.entity.User;

import java.time.Duration;
import java.util.Optional;

class MailSendingServiceImplTest extends BaseSpringBootTest {
    @SneakyThrows
    @Test
    void sendEmailWithUrlShouldWork() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFullName("Test test test");
        user.setVerificationCode("12345");
        String url = "test.com";
        Awaitility.await()
                .atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> mailSendingServiceImpl.sendEmail(user, url));
    }

    @Test
    void sendEmailWithCurrentUserForResettingPasswordShouldWork() {
        User user = new User();
        user.setEmail("test@test.com");
        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        User currentUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        Awaitility.await()
                .atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> mailSendingServiceImpl.sendEmail(currentUser));
    }
}

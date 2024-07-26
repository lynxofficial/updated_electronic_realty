package ru.realty.erealty.redis.service.mail.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MailSendingServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void sendEmailCacheableShouldWork() {
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        User currentUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        mailSendingServiceImpl.sendEmail(currentUser);
        mailSendingServiceImpl.sendEmail(currentUser);

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(userRepository, times(2)).findByEmail(user.getEmail());
    }
}

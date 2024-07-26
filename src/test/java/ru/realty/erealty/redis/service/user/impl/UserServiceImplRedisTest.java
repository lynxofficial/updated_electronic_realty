package ru.realty.erealty.redis.service.user.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void findAllCacheableShouldWork() {
        userServiceImpl.findAll();
        userServiceImpl.findAll();

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findByEmailCacheableShouldWork() {
        String email = UserEmail.DEFAULT_EMAIL;
        User user = DataProvider.userBuilder().build();
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        userServiceImpl.findByEmail(email);

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void verifyAccountCacheableShouldWork() {
        String verificationCode = "12345";
        User user = DataProvider.userBuilder()
                .verificationCode(verificationCode)
                .build();

        when(userRepository.findByVerificationCode(verificationCode))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user))
                .thenReturn(user);

        userServiceImpl.verifyAccount(verificationCode);
        userServiceImpl.verifyAccount(verificationCode);

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(userRepository, times(1)).findByVerificationCode("12345");
    }
}

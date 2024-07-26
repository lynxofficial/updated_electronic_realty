package ru.realty.erealty.redis.service.custom.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomTokenServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void findByTokenCacheableShouldWork() {
        PasswordResetToken passwordResetToken = DataProvider.passwordResetTokenBuilder()
                .token("123").build();
        when(customTokenRepository.findByToken("123")).thenReturn(passwordResetToken);

        customTokenServiceImpl.findByToken("123");
        customTokenServiceImpl.findByToken("123");

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(customTokenRepository, times(1)).findByToken("123");
    }
}

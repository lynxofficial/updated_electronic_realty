package ru.realty.erealty.redis.service.realty.impl;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RealtyObjectServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void findAllCacheableShouldWork() {
        realtyObjectServiceImpl.findAll();
        realtyObjectServiceImpl.findAll();

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(realtyObjectRepository, times(1)).findAll();
    }

    @Test
    void buyRealtyObjectCacheableShouldWork() {
        when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        try {
            realtyObjectServiceImpl.buyRealtyObject("0");
            realtyObjectServiceImpl.buyRealtyObject("0");
        } catch (RealtyObjectNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(realtyObjectRepository, times(1)).findById(0);
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectWithDigitalSignatureCacheableShouldWork() {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .realtyObjects(new ArrayList<>())
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(targetUser)
                .price(BigDecimal.valueOf(1_000_000L))
                .build();
        targetUser.getRealtyObjects().add(realtyObject);

        User currentUser = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();

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

        try {
            realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(0);
            realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(0);
        } catch (RealtyObjectNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(realtyObjectRepository, times(1)).findById(0);
    }
}

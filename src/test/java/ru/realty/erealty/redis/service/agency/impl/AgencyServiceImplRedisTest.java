package ru.realty.erealty.redis.service.agency.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AgencyServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void findAllCacheableShouldWork() {
        agencyServiceImpl.findAll();
        agencyServiceImpl.findAll();

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(agencyRepository, times(1)).findAll();
    }
}

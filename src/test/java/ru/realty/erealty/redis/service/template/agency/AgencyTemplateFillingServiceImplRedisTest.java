package ru.realty.erealty.redis.service.template.agency;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AgencyTemplateFillingServiceImplRedisTest extends BaseSpringBootTest {
    @BeforeEach
    public void verifyNoInteractionsWithMockBeans() {
        verifyNoMoreInteractions(agencyRepository);
    }

    @Test
    void fillAgencyTemplateCacheableShouldWork() {
        Model model = new ExtendedModelMap();

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        assertThat(redisCacheManager.getCacheNames())
                .isNotNull();
        verify(agencyRepository, times(1)).findAll();
    }

    @Test
    void fillAgencyTemplateCacheableWithTimeToLiveShouldWork() {
        Model model = new ExtendedModelMap();

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        verify(agencyRepository, times(1)).findAll();

        Awaitility.await()
                .pollInterval(Duration.ofSeconds(10L))
                .atMost(Duration.ofSeconds(20L));

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        verify(agencyRepository, times(1)).findAll();
    }
}

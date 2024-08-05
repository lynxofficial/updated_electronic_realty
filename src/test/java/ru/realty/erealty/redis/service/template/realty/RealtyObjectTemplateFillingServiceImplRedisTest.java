package ru.realty.erealty.redis.service.template.realty;

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

public class RealtyObjectTemplateFillingServiceImplRedisTest extends BaseSpringBootTest {
    @BeforeEach
    public void verifyNoInteractionsWithMockBeans() {
        verifyNoMoreInteractions(realtyObjectRepository);
    }

    @Test
    void fillDeleteRealtyObjectsTemplateCacheableShouldWork() {
        Model model = new ExtendedModelMap();

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);
        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        assertThat(redisCacheManager.getCacheNames())
                .isNotNull();
        verify(realtyObjectRepository, times(1)).findAll();
    }

    @Test
    void fillDeleteRealtyObjectsTemplateCacheableWithTimeToLiveShouldWork() {
        Model model = new ExtendedModelMap();

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);
        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        verify(realtyObjectRepository, times(1)).findAll();

        Awaitility.await()
                .pollInterval(Duration.ofSeconds(10L))
                .atMost(Duration.ofSeconds(20L));

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);
        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        verify(realtyObjectRepository, times(1)).findAll();
    }
}

package ru.realty.erealty.redis.service.template.home;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HomeTemplateFillingServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void fillHomeTemplateCacheableShouldWork() {
        Model model = new ExtendedModelMap();

        homeTemplateFillingService.fillHomeTemplate(model);
        homeTemplateFillingService.fillHomeTemplate(model);

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(realtyObjectRepository, times(1)).findAll();
    }
}

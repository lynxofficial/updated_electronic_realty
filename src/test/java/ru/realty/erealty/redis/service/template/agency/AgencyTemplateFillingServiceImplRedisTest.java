package ru.realty.erealty.redis.service.template.agency;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AgencyTemplateFillingServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void fillAgencyTemplateCacheableShouldWork() {
        Model model = new ExtendedModelMap();

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(agencyRepository, times(1)).findAll();
    }
}

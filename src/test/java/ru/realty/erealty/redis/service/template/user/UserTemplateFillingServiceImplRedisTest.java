package ru.realty.erealty.redis.service.template.user;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTemplateFillingServiceImplRedisTest extends BaseSpringBootTest {
    @Test
    void fillDeleteUserTemplateCacheableShouldWork() {
        Model model = new ExtendedModelMap();

        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);
        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);

        assertThat(cacheManager.getCacheNames())
                .isNotNull();
        verify(userRepository, times(1)).findAll();
    }
}

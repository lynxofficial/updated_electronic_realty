package ru.realty.erealty.service.template.user.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

public class UserTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillDeleteUserTemplateShouldWork() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        Model model = new ExtendedModelMap();

        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);

        Assertions.assertThat(model.containsAttribute("users"))
                .isTrue();
    }

    @Test
    void fillDeleteUserTemplateShouldNotWork() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        Model model = new ExtendedModelMap();
        model.addAttribute("defaultUser");

        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);

        Assertions.assertThat(model.containsAttribute("users"))
                .isTrue();
        Assertions.assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }
}

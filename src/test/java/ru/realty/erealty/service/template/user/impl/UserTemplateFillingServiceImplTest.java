package ru.realty.erealty.service.template.user.impl;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillDeleteUserTemplateShouldWork() {
        when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        Model model = new ExtendedModelMap();

        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);

        assertThat(model.containsAttribute("users"))
                .isTrue();
    }

    @Test
    void fillDeleteUserTemplateShouldNotWork() {
        when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        Model model = new ExtendedModelMap();
        model.addAttribute("defaultUser");

        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);

        assertThat(model.containsAttribute("users"))
                .isTrue();
        assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }
}

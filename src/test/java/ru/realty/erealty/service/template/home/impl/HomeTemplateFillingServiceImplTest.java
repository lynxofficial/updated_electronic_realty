package ru.realty.erealty.service.template.home.impl;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class HomeTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillHomeTemplateShouldWork() {
        when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()
                ));

        Model model = new ExtendedModelMap();

        homeTemplateFillingService.fillHomeTemplate(model);

        assertThat(model.containsAttribute("realtyObjects"))
                .isTrue();
    }

    @Test
    void fillHomeTemplateShouldNotWork() {
        when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        Model model = new ExtendedModelMap();
        model.addAttribute("defaultAttribute");

        homeTemplateFillingService.fillHomeTemplate(model);

        assertThat(model.asMap().size()).isNotEqualTo(1);
    }
}

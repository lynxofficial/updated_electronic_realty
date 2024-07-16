package ru.realty.erealty.service.template.home.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

class HomeTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillHomeTemplateShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()
                ));

        Model model = new ExtendedModelMap();

        homeTemplateFillingService.fillHomeTemplate(model);

        Assertions.assertTrue(model.containsAttribute("realtyObjects"));
    }

    @Test
    void fillHomeTemplateShouldNotWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        Model model = new ExtendedModelMap();
        model.addAttribute("defaultAttribute");

        homeTemplateFillingService.fillHomeTemplate(model);

        Assertions.assertNotEquals(1, model.asMap().size());
    }
}

package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.RealtyObject;

import java.util.List;

class HomeTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillHomeTemplateShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(new RealtyObject(), new RealtyObject(), new RealtyObject()));
        Model model = new ExtendedModelMap();
        homeTemplateFillingService.fillHomeTemplate(model);
        Assertions.assertTrue(model.containsAttribute("realtyObjects"));
    }
}

package ru.realty.erealty.service.template.realty.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.util.DataProvider;

import java.util.List;
import java.util.Optional;

class RealtyObjectTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillRealtyObjectTemplateThrowsException() {
        Model model = new ExtendedModelMap();

        Assertions.assertThrows(NullPointerException.class,
                () -> realtyObjectTemplateFillingServiceImpl.fillRealtyObjectTemplate(model));
    }

    @Test
    void fillBuyRealtyObjectTemplateDoesNotThrowException() {
        Model model = new ExtendedModelMap();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(realtyObject));

        Assertions.assertDoesNotThrow(() -> realtyObjectTemplateFillingServiceImpl.fillBuyRealtyObjectTemplate(model,
                "0"));
    }

    @Test
    void fillBuyRealtyObjectTemplateThrowsException() {
        Model model = new ExtendedModelMap();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(realtyObject));

        Assertions.assertThrows(RealtyObjectNotFoundException.class,
                () -> realtyObjectTemplateFillingServiceImpl.fillBuyRealtyObjectTemplate(model, "1"));
    }

    @Test
    void fillDeleteRealtyObjectsTemplateShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()
                ));

        Model model = new ExtendedModelMap();

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        Assertions.assertTrue(model.containsAttribute("realtyObjects"));
    }

    @Test
    void fillDeleteRealtyObjectsTemplateShouldNotWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()
                ));

        Model model = new ExtendedModelMap();
        model.addAttribute("newAttribute");

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        Assertions.assertNotEquals(1, model.asMap().size());
    }
}

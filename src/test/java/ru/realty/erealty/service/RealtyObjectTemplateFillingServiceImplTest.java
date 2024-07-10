package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.util.List;
import java.util.Optional;

class RealtyObjectTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillRealtyObjectTemplateThrowsException() {
        Model model = new ExtendedModelMap();
        Assertions.assertThrows(NullPointerException.class,
                () -> realtyObjectTemplateFillingServiceImpl.fillRealtyObjectTemplate(model));
    }

    @SneakyThrows
    @Test
    void fillBuyRealtyObjectTemplateDoesNotThrowException() {
        Model model = new ExtendedModelMap();
        RealtyObject realtyObject = new RealtyObject();
        realtyObject.setUser(new User());
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(realtyObject));
        realtyObjectTemplateFillingServiceImpl.fillBuyRealtyObjectTemplate(model, "0");
        Assertions.assertDoesNotThrow(() -> realtyObjectTemplateFillingServiceImpl.fillBuyRealtyObjectTemplate(model,
                "0"));
    }

    @Test
    void fillDeleteRealtyObjectsTemplateShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(new RealtyObject(), new RealtyObject()));
        Model model = new ExtendedModelMap();
        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);
        Assertions.assertTrue(model.containsAttribute("realtyObjects"));
    }
}

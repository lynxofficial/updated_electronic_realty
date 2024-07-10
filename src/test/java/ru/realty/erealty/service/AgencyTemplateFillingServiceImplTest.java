package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.Agency;

import java.util.List;

class AgencyTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillAgencyTemplateShouldWork() {
        Mockito.when(agencyRepository.findAll()).thenReturn(List.of(new Agency(), new Agency()));
        Model model = new ExtendedModelMap();
        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        Assertions.assertTrue(model.containsAttribute("agencies"));
    }
}

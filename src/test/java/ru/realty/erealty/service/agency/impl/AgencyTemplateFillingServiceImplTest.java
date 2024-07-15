package ru.realty.erealty.service.agency.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

class AgencyTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillAgencyTemplateShouldWork() {
        Mockito.when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));
        Model model = new ExtendedModelMap();

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        Assertions.assertTrue(model.containsAttribute("agencies"));
    }

    @Test
    void fillAgencyTemplateShouldNotWork() {
        Mockito.when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));

        Model model = new ExtendedModelMap();
        model.addAttribute("testAttribute");

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        Assertions.assertNotEquals(1, model.asMap().size());
    }
}

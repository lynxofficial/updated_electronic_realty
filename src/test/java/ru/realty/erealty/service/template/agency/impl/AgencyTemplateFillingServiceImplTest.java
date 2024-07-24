package ru.realty.erealty.service.template.agency.impl;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AgencyTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillAgencyTemplateShouldWork() {
        when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));
        Model model = new ExtendedModelMap();

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        assertThat(model.containsAttribute("agencies"))
                .isTrue();
    }

    @Test
    void fillAgencyTemplateShouldNotWork() {
        when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));

        Model model = new ExtendedModelMap();
        model.addAttribute("testAttribute");

        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);

        assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }
}

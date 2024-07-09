package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.repository.AgencyRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AgencyTemplateFillingServiceImplTest {
    @Mock
    private AgencyRepository agencyRepository;
    @InjectMocks
    private AgencyTemplateFillingServiceImpl agencyTemplateFillingServiceImpl;

    @Test
    public void fillAgencyTemplateTest() {
        final Model model = new ExtendedModelMap();
        Mockito.when(agencyRepository.findAll()).thenReturn(List.of(new Agency(), new Agency()));
        agencyTemplateFillingServiceImpl.fillAgencyTemplate(model);
        Assertions.assertTrue(model.containsAttribute("agencies"));
    }
}

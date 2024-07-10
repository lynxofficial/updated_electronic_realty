package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.entity.Agency;

import java.util.List;

class AgencyServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(agencyRepository.findAll()).thenReturn(List.of(new Agency(), new Agency()));
        List<Agency> agencies = agencyRepository.findAll();
        Assertions.assertEquals(agencies, agencyServiceImpl.findAll());
    }
}

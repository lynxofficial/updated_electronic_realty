package ru.realty.erealty.service.agency.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

class AgencyServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));

        List<Agency> agencies = agencyRepository.findAll();

        Assertions.assertThat(agencies)
                .isEqualTo(agencyServiceImpl.findAll());
    }

    @Test
    void findAllThrowsException() {
        Mockito.when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));

        List<Agency> agencies = agencyServiceImpl.findAll();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> agencies.add(DataProvider.agencyBuilder().build()));
    }
}

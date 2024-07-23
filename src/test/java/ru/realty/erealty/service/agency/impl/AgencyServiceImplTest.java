package ru.realty.erealty.service.agency.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

class AgencyServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));

        List<Agency> agencies = agencyRepository.findAll();

        assertThat(agencies)
                .isEqualTo(agencyServiceImpl.findAll());
    }

    @Test
    void findAllThrowsException() {
        when(agencyRepository.findAll())
                .thenReturn(List.of(DataProvider.agencyBuilder().build(), DataProvider.agencyBuilder().build()));

        List<Agency> agencies = agencyServiceImpl.findAll();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> agencies.add(DataProvider.agencyBuilder().build()));
    }
}

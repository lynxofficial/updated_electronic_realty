package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.repository.AgencyRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AgencyServiceImplTest {
    @Mock
    private AgencyRepository agencyRepository;
    @InjectMocks
    private AgencyServiceImpl agencyServiceImpl;

    @Test
    public void findAllTest() {
        Mockito.when(agencyRepository.findAll()).thenReturn(List.of(new Agency(), new Agency()));
        List<Agency> agencies = agencyRepository.findAll();
        Assertions.assertEquals(agencies, agencyServiceImpl.findAll());
    }
}

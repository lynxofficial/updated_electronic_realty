package ru.realty.erealty.service.agency.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.service.agency.AgencyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;

    @Override
    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }
}

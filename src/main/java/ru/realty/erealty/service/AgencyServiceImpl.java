package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.repository.AgencyRepository;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;

    @Override
    public Agency saveAgency(Agency agency) {
        return agencyRepository.save(agency);
    }
}

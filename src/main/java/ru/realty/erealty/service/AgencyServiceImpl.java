package ru.realty.erealty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.repository.AgencyRepository;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyRepository agencyRepository;

    @Override
    public Agency saveAgency(Agency agency) {
        return agencyRepository.save(agency);
    }
}

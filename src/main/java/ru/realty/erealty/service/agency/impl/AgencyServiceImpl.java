package ru.realty.erealty.service.agency.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.service.agency.AgencyService;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "AgencyServiceImplCache")
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;

    @Override
    @Cacheable
    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }
}

package ru.realty.erealty.service.template.agency.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.mapper.AgencyMapper;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.service.template.agency.AgencyTemplateFillingService;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "AgencyTemplateFillingServiceImplCache", cacheManager = "redisCacheManager")
public class AgencyTemplateFillingServiceImpl implements AgencyTemplateFillingService {
    private final AgencyRepository agencyRepository;
    private final AgencyMapper agencyMapper;

    @Override
    @Cacheable
    public void fillAgencyTemplate(final Model model) {
        model.addAttribute("agencies", agencyMapper.toAgencyResponseList(agencyRepository.findAll()));
    }
}

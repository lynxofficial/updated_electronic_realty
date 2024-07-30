package ru.realty.erealty.service.template.home.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.mapper.RealtyObjectMapper;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.service.template.home.HomeTemplateFillingService;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "HomeTemplateFillingServiceImplCache", cacheManager = "redisCacheManager")
public class HomeTemplateFillingServiceImpl implements HomeTemplateFillingService {
    private final RealtyObjectRepository realtyObjectRepository;
    private final RealtyObjectMapper realtyObjectMapper;

    @Override
    @Cacheable
    public void fillHomeTemplate(final Model model) {
        model.addAttribute("realtyObjects", realtyObjectMapper
                .toRealtyObjectResponseList(realtyObjectRepository.findAll()));
    }
}

package ru.realty.erealty.service.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.service.custom.CustomTokenService;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "CustomTokenServiceImplCache")
public class CustomTokenServiceImpl implements CustomTokenService {
    private final CustomTokenRepository customTokenRepository;

    @Override
    @Cacheable
    public PasswordResetToken findByToken(final String token) {
        return customTokenRepository.findByToken(token);
    }
}

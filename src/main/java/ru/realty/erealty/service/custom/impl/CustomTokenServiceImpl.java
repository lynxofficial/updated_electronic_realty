package ru.realty.erealty.service.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.service.custom.CustomTokenService;

@Service
@RequiredArgsConstructor
public class CustomTokenServiceImpl implements CustomTokenService {
    private final CustomTokenRepository customTokenRepository;

    @Override
    public PasswordResetToken findByToken(final String token) {
        return customTokenRepository.findByToken(token);
    }
}

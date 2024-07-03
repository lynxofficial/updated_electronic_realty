package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.repository.CustomTokenRepository;

@Service
@RequiredArgsConstructor
public class CustomTokenServiceImpl implements CustomTokenService {
    private final CustomTokenRepository customTokenRepository;

    @Override
    public PasswordResetToken findByToken(String token) {
        return customTokenRepository.findByToken(token);
    }
}

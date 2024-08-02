package ru.realty.erealty.service.token.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.service.token.ResetTokenGenerationService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetTokenGenerationServiceImpl implements ResetTokenGenerationService {
    private final CustomTokenRepository customTokenRepository;

    @Override
    @Transactional
    public String generateResetToken(final User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setUser(user);
        customTokenRepository.save(resetToken);
        String endpointUrl = "http://localhost:8080/resetPassword";
        return endpointUrl + "/" + resetToken.getToken();
    }
}

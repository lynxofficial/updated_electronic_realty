package ru.realty.erealty.service.token;

import ru.realty.erealty.entity.User;

public interface ResetTokenGenerationService {

    String generateResetToken(User user);
}

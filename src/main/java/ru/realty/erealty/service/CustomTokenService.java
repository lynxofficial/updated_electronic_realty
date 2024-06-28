package ru.realty.erealty.service;

import ru.realty.erealty.entity.PasswordResetToken;

public interface CustomTokenService {

    PasswordResetToken findByToken(String token);
}

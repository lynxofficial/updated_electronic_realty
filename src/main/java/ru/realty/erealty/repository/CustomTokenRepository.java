package ru.realty.erealty.repository;

import ru.realty.erealty.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);
}


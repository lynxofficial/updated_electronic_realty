package ru.realty.erealty.repository;

import lombok.NonNull;
import ru.realty.erealty.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @NonNull
    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String verificationCode);

    @Override
    @NonNull
    Optional<User> findById(@NonNull Integer id);

    @Override
    void deleteById(@NonNull Integer id);
}

package ru.realty.erealty.repository;

import org.springframework.lang.NonNull;
import ru.realty.erealty.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @NonNull
    List<User> findAll();

    User findByEmail(String email);

    User findByVerificationCode(String verificationCode);

    @Override
    @NonNull
    Optional<User> findById(@NonNull Integer integer);

    @Override
    void deleteById(@NonNull Integer userId);
}

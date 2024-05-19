package ru.realty.erealty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.util.List;
import java.util.Optional;

public interface RealtyObjectRepository extends JpaRepository<RealtyObject, Integer> {

    @Override
    @NonNull
    List<RealtyObject> findAll();

    List<RealtyObject> findAllByUser(User user);

    @Override
    @NonNull
    Optional<RealtyObject> findById(@NonNull Integer realtyObjectId);

    @Override
    void delete(@NonNull RealtyObject entity);
}

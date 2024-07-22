package ru.realty.erealty.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.realty.erealty.entity.Agency;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

    @Override
    @NonNull
    List<Agency> findAll();
}

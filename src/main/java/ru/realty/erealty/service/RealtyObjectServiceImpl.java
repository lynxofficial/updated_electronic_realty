package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.repository.RealtyObjectRepository;

@Service
@RequiredArgsConstructor
public class RealtyObjectServiceImpl implements RealtyObjectService {
    private final RealtyObjectRepository realtyObjectRepository;

    @Override
    public void saveRealtyObject(RealtyObject realtyObject) {
        realtyObjectRepository.save(realtyObject);
    }


    @Override
    public void delete(RealtyObject realtyObject) {
        realtyObjectRepository.delete(realtyObject);
    }
}

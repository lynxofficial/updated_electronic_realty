package ru.realty.erealty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.repository.RealtyObjectRepository;

@Service
public class RealtyObjectServiceImpl implements RealtyObjectService {
    @Autowired
    private RealtyObjectRepository realtyObjectRepository;

    @Override
    public void saveRealtyObject(RealtyObject realtyObject) {
        realtyObjectRepository.save(realtyObject);
    }


    @Override
    public void delete(RealtyObject realtyObject) {
        realtyObjectRepository.delete(realtyObject);
    }
}

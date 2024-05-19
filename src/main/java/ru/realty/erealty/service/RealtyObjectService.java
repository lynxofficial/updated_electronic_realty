package ru.realty.erealty.service;

import ru.realty.erealty.entity.RealtyObject;

public interface RealtyObjectService {

    void saveRealtyObject(RealtyObject realtyObject);

    void delete(RealtyObject realtyObject);
}

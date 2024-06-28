package ru.realty.erealty.service;

import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.io.IOException;
import java.util.List;

public interface RealtyObjectService {

    List<RealtyObject> findAll();

    List<RealtyObject> findAllByUser(User user);

    RealtyObject buyRealtyObject(String realtyObjectId);

    void sellRealtyObject(User user, RealtyObject realtyObject, MultipartFile file) throws IOException;

    Boolean buyRealtyObjectWithDigitalSignature(RealtyObject realtyObject);

    void deleteRealtyObject(RealtyObject realtyObject);
}

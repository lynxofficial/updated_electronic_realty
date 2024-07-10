package ru.realty.erealty.service;

import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;

import java.io.IOException;
import java.util.List;

public interface RealtyObjectService {

    List<RealtyObject> findAll();

    RealtyObject buyRealtyObject(String realtyObjectId) throws RealtyObjectNotFoundException;

    void sellRealtyObject(User user, RealtyObject realtyObject, MultipartFile file) throws IOException;

    Boolean buyRealtyObjectWithDigitalSignature(RealtyObject realtyObject) throws RealtyObjectNotFoundException;

    void deleteRealtyObject(RealtyObject realtyObject) throws RealtyObjectNotFoundException;
}

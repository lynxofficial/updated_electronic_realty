package ru.realty.erealty.service;

import ru.realty.erealty.entity.User;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface DigitalSignatureGenerationService {

    void generateDigitalSignature(String passwordForDigitalSignature, User user) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException;
}

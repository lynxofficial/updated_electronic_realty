package ru.realty.erealty.service.signature.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.signature.DigitalSignatureGenerationService;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.KeyPair;

@Service
@RequiredArgsConstructor
public class DigitalSignatureGenerationServiceImpl implements DigitalSignatureGenerationService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void generateDigitalSignature(final String passwordForDigitalSignature, final User user)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256WithDSA");
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        signature.initSign(keyPair.getPrivate(), secureRandom);
        byte[] data = passwordForDigitalSignature.getBytes(StandardCharsets.UTF_8);
        signature.update(data);
        byte[] digitalSignature = signature.sign();
        user.setDigitalSignature(new String(digitalSignature, StandardCharsets.UTF_16));
        userRepository.save(user);
    }
}

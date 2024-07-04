package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.*;

@Service
@RequiredArgsConstructor
public class DigitalSignatureGenerationServiceImpl implements DigitalSignatureGenerationService {
    private final UserRepository userRepository;

    @Override
    public void generateDigitalSignature(String passwordForDigitalSignature, User user) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
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

package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DigitalSignatureGenerationServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DigitalSignatureGenerationServiceImpl digitalSignatureGenerationServiceImpl;

    @Test
    public void generateDigitalSignatureTest() throws NoSuchAlgorithmException,
            SignatureException, InvalidKeyException {
        final String passwordForDigitalSignature = "123";
        final User user = new User();
        user.setEmail("123");
        Mockito.lenient().when(userRepository.findByEmail("123")).thenReturn(Optional.of(user));
        digitalSignatureGenerationServiceImpl.generateDigitalSignature(passwordForDigitalSignature, user);
        Assertions.assertNotNull(user.getDigitalSignature());
    }
}

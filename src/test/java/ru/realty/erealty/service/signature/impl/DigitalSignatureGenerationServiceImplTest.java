package ru.realty.erealty.service.signature.impl;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

class DigitalSignatureGenerationServiceImplTest extends BaseSpringBootTest {
    @Test
    void generateDigitalSignatureShouldWork() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String passwordForDigitalSignature = "123";
        User user = DataProvider.userBuilder()
                .email("123@mail.ru")
                .build();
        when(userRepository.findByEmail("123@mail.ru")).thenReturn(Optional.of(user));

        digitalSignatureGenerationServiceImpl.generateDigitalSignature(passwordForDigitalSignature, user);

        assertThat(user.getDigitalSignature())
                .isNotNull();
    }

    @Test
    void generateDigitalSignatureThrowsException() {
        User user = DataProvider.userBuilder()
                .email("123@mail.ru")
                .passwordForDigitalSignature(null)
                .build();
        when(userRepository.findByEmail("123@mail.ru"))
                .thenReturn(Optional.of(user));

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> digitalSignatureGenerationServiceImpl
                        .generateDigitalSignature(user.getPasswordForDigitalSignature(), user));
    }
}

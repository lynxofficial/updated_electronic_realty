package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.realty.erealty.entity.User;

import java.util.Optional;

class DigitalSignatureGenerationServiceImplTest extends BaseSpringBootTest {
    @Test
    @SneakyThrows
    void generateDigitalSignatureShouldWork() {
        final String passwordForDigitalSignature = "123";
        final User user = new User();
        user.setEmail("123@mail.ru");
        Mockito.when(userRepository.findByEmail("123@mail.ru")).thenReturn(Optional.of(user));
        digitalSignatureGenerationServiceImpl.generateDigitalSignature(passwordForDigitalSignature, user);
        Assertions.assertNotNull(user.getDigitalSignature());
    }
}

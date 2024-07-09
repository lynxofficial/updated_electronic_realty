package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.repository.CustomTokenRepository;

@ExtendWith(MockitoExtension.class)
public class CustomTokenServiceImplTest {
    @Mock
    private CustomTokenRepository customTokenRepository;
    @InjectMocks
    private CustomTokenServiceImpl customTokenServiceImpl;

    @Test
    public void findByTokenTest() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken("123");
        Mockito.when(customTokenRepository.findByToken("123")).thenReturn(passwordResetToken);
        Assertions.assertEquals(passwordResetToken, customTokenServiceImpl.findByToken("123"));
    }
}

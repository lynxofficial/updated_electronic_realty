package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;

import javax.security.auth.kerberos.KerberosPrincipal;
import java.security.Principal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommonUserAuthorizationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private CommonUserAuthorizationService commonUserAuthorizationService;

    @Test
    public void setCommonUserTest() {
        User user = new User();
        user.setEmail("test@test.com");
        Mockito.lenient().when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Mockito.when(userServiceImpl.findByEmail("test@test.com")).thenReturn(user);
        Principal principal = new KerberosPrincipal("test@test.com");
        final Model model = new ExtendedModelMap();
        commonUserAuthorizationService.setCommonUser(principal, model);
        Assertions.assertTrue(model.containsAttribute("user"));
    }
}

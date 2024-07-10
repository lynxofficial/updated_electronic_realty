package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.User;

import javax.security.auth.kerberos.KerberosPrincipal;
import java.security.Principal;
import java.util.Optional;

class CommonUserAuthorizationServiceTest extends BaseSpringBootTest {
    @Test
    void setCommonUserShouldWork() {
        User user = new User();
        user.setEmail("test@test.com");
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Principal principal = new KerberosPrincipal("test@test.com");
        Model model = new ExtendedModelMap();
        commonUserAuthorizationService.setCommonUser(principal, model);
        Assertions.assertTrue(model.containsAttribute("user"));
    }
}

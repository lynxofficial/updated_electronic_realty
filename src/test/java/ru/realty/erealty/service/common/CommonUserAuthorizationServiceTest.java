package ru.realty.erealty.service.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import javax.security.auth.kerberos.KerberosPrincipal;
import java.security.Principal;
import java.util.Optional;

class CommonUserAuthorizationServiceTest extends BaseSpringBootTest {
    @Test
    void setCommonUserShouldWork() {
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Principal principal = new KerberosPrincipal("test@test.com");
        Model model = new ExtendedModelMap();

        commonUserAuthorizationService.setCommonUser(principal, model);

        Assertions.assertThat(model.containsAttribute("user"))
                .isTrue();
    }

    @Test
    void setCommonUserShouldNotWork() {
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Principal principal = new KerberosPrincipal("test@test.com");
        Model model = new ExtendedModelMap();
        model.addAttribute("newAttribute");

        commonUserAuthorizationService.setCommonUser(principal, model);

        Assertions.assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }

    @Test
    void setCommonUserShouldNotWorkWithPrincipalEmptyName() {
        User user = DataProvider.userBuilder().build();
        Principal principal = Mockito.mock(Principal.class);
        Model model = new ExtendedModelMap();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        Mockito.when(principal.getName())
                .thenReturn("");

        commonUserAuthorizationService.setCommonUser(principal, model);

        Assertions.assertThat(model.containsAttribute("user"))
                .isFalse();
    }
}

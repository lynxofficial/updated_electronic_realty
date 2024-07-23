package ru.realty.erealty.service.common;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import javax.security.auth.kerberos.KerberosPrincipal;
import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CommonUserAuthorizationServiceTest extends BaseSpringBootTest {
    @Test
    void setCommonUserShouldWork() {
        User user = DataProvider.userBuilder().build();
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));

        Principal principal = new KerberosPrincipal(UserEmail.DEFAULT_EMAIL);
        Model model = new ExtendedModelMap();

        commonUserAuthorizationService.setCommonUser(principal, model);

        assertThat(model.containsAttribute("user"))
                .isTrue();
    }

    @Test
    void setCommonUserShouldNotWork() {
        User user = DataProvider.userBuilder().build();
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));

        Principal principal = new KerberosPrincipal(UserEmail.DEFAULT_EMAIL);
        Model model = new ExtendedModelMap();
        model.addAttribute("newAttribute");

        commonUserAuthorizationService.setCommonUser(principal, model);

        assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }

    @Test
    void setCommonUserShouldNotWorkWithPrincipalEmptyName() {
        User user = DataProvider.userBuilder().build();
        Principal principal = Mockito.mock(Principal.class);
        Model model = new ExtendedModelMap();
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));
        when(principal.getName())
                .thenReturn("");

        commonUserAuthorizationService.setCommonUser(principal, model);

        assertThat(model.containsAttribute("user"))
                .isFalse();
    }
}

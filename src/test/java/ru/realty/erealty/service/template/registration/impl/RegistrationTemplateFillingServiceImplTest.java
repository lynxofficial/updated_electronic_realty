package ru.realty.erealty.service.template.registration.impl;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class RegistrationTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillRegistrationTemplateShouldWork() {
        User user = DataProvider.userBuilder()
                .verificationCode("12345")
                .build();
        when(userRepository.findByVerificationCode(user.getVerificationCode()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user))
                .thenReturn(user);

        Model model = new ExtendedModelMap();

        registrationTemplateFillingServiceImpl.fillRegistrationTemplate(user.getVerificationCode(), model);

        assertThat(model.containsAttribute("msg"))
                .isTrue();
    }

    @Test
    void fillRegistrationTemplateShouldNotWork() {
        User user = DataProvider.userBuilder()
                .verificationCode("12345")
                .build();
        when(userRepository.findByVerificationCode(user.getVerificationCode()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user))
                .thenReturn(user);

        Model model = new ExtendedModelMap();
        model.addAttribute("standardAttribute");

        registrationTemplateFillingServiceImpl.fillRegistrationTemplate(user.getVerificationCode(), model);

        assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }
}

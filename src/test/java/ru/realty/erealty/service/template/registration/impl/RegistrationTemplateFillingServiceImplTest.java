package ru.realty.erealty.service.template.registration.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.util.Optional;

class RegistrationTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillRegistrationTemplateShouldWork() {
        User user = DataProvider.userBuilder()
                .verificationCode("12345")
                .build();
        Mockito.when(userRepository.findByVerificationCode(user.getVerificationCode()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Model model = new ExtendedModelMap();

        registrationTemplateFillingServiceImpl.fillRegistrationTemplate(user.getVerificationCode(), model);

        Assertions.assertTrue(model.containsAttribute("msg"));
    }

    @Test
    void fillRegistrationTemplateShouldNotWork() {
        User user = DataProvider.userBuilder()
                .verificationCode("12345")
                .build();
        Mockito.when(userRepository.findByVerificationCode(user.getVerificationCode()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Model model = new ExtendedModelMap();
        model.addAttribute("standardAttribute");

        registrationTemplateFillingServiceImpl.fillRegistrationTemplate(user.getVerificationCode(), model);

        Assertions.assertNotEquals(1, model.asMap().size());
    }
}

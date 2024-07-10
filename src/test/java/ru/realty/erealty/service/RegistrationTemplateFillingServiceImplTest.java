package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.User;

import java.util.Optional;

class RegistrationTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillRegistrationTemplateShouldWork() {
        String code = "12345";
        User user = new User();
        user.setVerificationCode(code);
        Mockito.when(userRepository.findByVerificationCode(user.getVerificationCode()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Model model = new ExtendedModelMap();
        registrationTemplateFillingServiceImpl.fillRegistrationTemplate(user.getVerificationCode(), model);
        Assertions.assertTrue(model.containsAttribute("msg"));
    }
}

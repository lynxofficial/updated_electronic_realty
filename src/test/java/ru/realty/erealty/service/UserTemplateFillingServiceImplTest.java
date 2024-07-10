package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.User;

import java.util.List;

public class UserTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    void fillDeleteUserTemplateShouldWork() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(new User(), new User()));
        Model model = new ExtendedModelMap();
        userTemplateFillingServiceImpl.fillDeleteUserTemplate(model);
        Assertions.assertTrue(model.containsAttribute("users"));
    }
}

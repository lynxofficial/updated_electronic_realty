package ru.realty.erealty.service.template.realty.impl;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.util.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

class RealtyObjectTemplateFillingServiceImplTest extends BaseSpringBootTest {
    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void fillRealtyObjectTemplateShouldWork() {
        final Model model = new ExtendedModelMap();
        User user = DataProvider.userBuilder()
                .realtyObjects(new ArrayList<>())
                .build();
        RealtyObject firstTestRealtyObject = DataProvider.realtyObjectBuilder().build();
        RealtyObject secondTestRealtyObject = DataProvider.realtyObjectBuilder()
                .id(1)
                .build();
        user.getRealtyObjects().add(firstTestRealtyObject);
        user.getRealtyObjects().add(secondTestRealtyObject);

        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(user));
        when(realtyObjectRepository.findAllByUser(user))
                .thenReturn(user.getRealtyObjects());

        realtyObjectTemplateFillingServiceImpl.fillRealtyObjectTemplate(model);

        assertThat(model.containsAttribute("realtyObjects"))
                .isTrue();
    }

    @Test
    void fillRealtyObjectTemplateThrowsException() {
        Model model = new ExtendedModelMap();

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> realtyObjectTemplateFillingServiceImpl.fillRealtyObjectTemplate(model));
    }

    @Test
    void fillBuyRealtyObjectTemplateDoesNotThrowException() {
        Model model = new ExtendedModelMap();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(realtyObject));

        assertThatNoException()
                .isThrownBy(() -> realtyObjectTemplateFillingServiceImpl
                        .fillBuyRealtyObjectTemplate(model, "0"));
    }

    @Test
    void fillBuyRealtyObjectTemplateThrowsException() {
        Model model = new ExtendedModelMap();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(realtyObject));

        assertThatExceptionOfType(RealtyObjectNotFoundException.class)
                .isThrownBy(() -> realtyObjectTemplateFillingServiceImpl
                        .fillBuyRealtyObjectTemplate(model, "1"));
    }

    @Test
    void fillDeleteRealtyObjectsTemplateShouldWork() {
        when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()
                ));

        Model model = new ExtendedModelMap();

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        assertThat(model.containsAttribute("realtyObjects"))
                .isTrue();
    }

    @Test
    void fillDeleteRealtyObjectsTemplateShouldNotWork() {
        when(realtyObjectRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()
                ));

        Model model = new ExtendedModelMap();
        model.addAttribute("newAttribute");

        realtyObjectTemplateFillingServiceImpl.fillDeleteRealtyObjectsTemplate(model);

        assertThat(model.asMap().size())
                .isNotEqualTo(1);
    }
}

package ru.realty.erealty.service.template.registration;

import org.springframework.ui.Model;

public interface RegistrationTemplateFillingService {

    void fillRegistrationTemplate(String code, Model model);
}

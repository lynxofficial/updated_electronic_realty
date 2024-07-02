package ru.realty.erealty.service;

import org.springframework.ui.Model;

public interface RegistrationTemplateFillingService {

    void fillRegistrationTemplate(String code, Model model);
}

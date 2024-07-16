package ru.realty.erealty.service.template.registration.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.service.user.UserVerificationService;
import ru.realty.erealty.service.template.registration.RegistrationTemplateFillingService;

@Service
@RequiredArgsConstructor
public class RegistrationTemplateFillingServiceImpl implements RegistrationTemplateFillingService {
    private final UserVerificationService userVerificationService;

    @Override
    public void fillRegistrationTemplate(final String code, final Model model) {
        Boolean isVerified = userVerificationService.verifyAccount(code);
        if (isVerified) {
            model.addAttribute("msg", "Ваш аккаунт успешно подтвержден!");
        } else {
            model.addAttribute("msg", "Ваш верификационный код может быть некорректным или"
                    + " использованным!");
        }
    }
}

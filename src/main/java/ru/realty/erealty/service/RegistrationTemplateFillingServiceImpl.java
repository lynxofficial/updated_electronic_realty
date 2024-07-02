package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class RegistrationTemplateFillingServiceImpl implements RegistrationTemplateFillingService {
    private final UserVerificationService userVerificationService;

    @Override
    public void fillRegistrationTemplate(String code, Model model) {
        Boolean isVerified = userVerificationService.verifyAccount(code);
        if (isVerified) {
            model.addAttribute("msg", "Ваш аккаунт успешно подтвержден!");
        } else {
            model.addAttribute("msg", "Ваш верификационный код может быть некорректным или" +
                    " использованным!");
        }
    }
}

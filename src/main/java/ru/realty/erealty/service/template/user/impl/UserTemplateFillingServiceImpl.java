package ru.realty.erealty.service.template.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.template.user.UserTemplateFillingService;

@Service
@RequiredArgsConstructor
public class UserTemplateFillingServiceImpl implements UserTemplateFillingService {
    private final UserRepository userRepository;

    @Override
    public void fillDeleteUserTemplate(final Model model) {
        model.addAttribute("users", userRepository.findAll());
    }
}

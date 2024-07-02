package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserTemplateFillingServiceImpl implements UserTemplateFillingService {
    private final UserRepository userRepository;

    @Override
    public void fillDeleteUserTemplate(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }
}

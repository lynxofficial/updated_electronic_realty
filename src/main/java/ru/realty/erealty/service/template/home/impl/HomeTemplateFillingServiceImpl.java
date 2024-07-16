package ru.realty.erealty.service.template.home.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.service.template.home.HomeTemplateFillingService;

@Service
@RequiredArgsConstructor
public class HomeTemplateFillingServiceImpl implements HomeTemplateFillingService {
    private final RealtyObjectRepository realtyObjectRepository;

    @Override
    public void fillHomeTemplate(final Model model) {
        model.addAttribute("realtyObjects", realtyObjectRepository.findAll());
    }
}

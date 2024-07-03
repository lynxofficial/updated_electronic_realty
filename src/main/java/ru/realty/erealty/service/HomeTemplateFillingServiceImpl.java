package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.repository.RealtyObjectRepository;

@Service
@RequiredArgsConstructor
public class HomeTemplateFillingServiceImpl implements HomeTemplateFillingService {
    private final RealtyObjectRepository realtyObjectRepository;

    @Override
    public void fillHomeTemplate(Model model) {
        model.addAttribute("realtyObjects", realtyObjectRepository.findAll());
    }
}

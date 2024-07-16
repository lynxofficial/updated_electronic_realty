package ru.realty.erealty.service.agency.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.service.agency.AgencyTemplateFillingService;

@Service
@RequiredArgsConstructor
public class AgencyTemplateFillingServiceImpl implements AgencyTemplateFillingService {
    private final AgencyRepository agencyRepository;

    @Override
    public void fillAgencyTemplate(final Model model) {
        model.addAttribute("agencies", agencyRepository.findAll());
    }
}

package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.realty.erealty.service.agency.AgencyTemplateFillingService;
import ru.realty.erealty.service.common.CommonUserAuthorizationService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AgencyController {
    private final AgencyTemplateFillingService agencyTemplateFillingService;
    private final CommonUserAuthorizationService commonUserAuthorizationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        commonUserAuthorizationService.setCommonUser(principal, model);
    }

    @GetMapping("/agencies")
    public String getAllAgencies(final Model model) {
        agencyTemplateFillingService.fillAgencyTemplate(model);
        return new ResponseEntity<>("agencies", HttpStatus.OK).getBody();
    }
}

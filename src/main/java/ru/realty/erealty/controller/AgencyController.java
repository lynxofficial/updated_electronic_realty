package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.AgencyTemplateFillingService;
import ru.realty.erealty.service.UserSearchingService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AgencyController {
    private final UserSearchingService userSearchingService;
    private final AgencyTemplateFillingService agencyTemplateFillingService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/agencies")
    public String getAllAgencies(final Model model) {
        agencyTemplateFillingService.fillAgencyTemplate(model);
        return new ResponseEntity<>("agencies", HttpStatus.OK).getBody();
    }
}

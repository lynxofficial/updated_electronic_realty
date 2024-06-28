package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.AgencyService;
import ru.realty.erealty.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AgencyController {
    private final AgencyService agencyService;
    private final UserService userService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/agencies")
    public String getAllAgencies(Model model) {
        List<Agency> agencies = agencyService.findAll();
        model.addAttribute("agencies", agencies);
        return "agencies";
    }
}

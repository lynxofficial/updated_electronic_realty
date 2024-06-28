package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AgencyController {
    private final AgencyRepository agencyRepository;
    private final UserRepository userRepository;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/agencies")
    public String getAllAgencies(Model model) {
        List<Agency> agencies = agencyRepository.findAll();
        model.addAttribute("agencies", agencies);
        return "agencies";
    }
}

package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/profile")
    public String profile() {
        return "admin_profile";
    }
}
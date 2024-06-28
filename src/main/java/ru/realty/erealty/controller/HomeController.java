package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.RealtyObjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.realty.erealty.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RealtyObjectRepository realtyObjectRepository;
    private final UserRepository userRepository;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        List<RealtyObject> realtyObjectList = realtyObjectRepository.findAll();
        model.addAttribute("realtyObjects", realtyObjectList);
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/signIn")
    public String login() {
        return "login";
    }
}

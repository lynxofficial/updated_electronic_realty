package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.realty.erealty.service.RealtyObjectService;
import ru.realty.erealty.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RealtyObjectService realtyObjectService;
    private final UserService userService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        List<RealtyObject> realtyObjectList = realtyObjectService.findAll();
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

package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.realty.erealty.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.realty.erealty.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/user/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/deleteUsers")
    public String deleteUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "deleteUsers";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer userId) {
        userService.deleteById(userId);
        return "redirect:/deleteUsers";
    }
}
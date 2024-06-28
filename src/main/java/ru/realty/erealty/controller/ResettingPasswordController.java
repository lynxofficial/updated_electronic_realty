package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.CustomTokenService;
import ru.realty.erealty.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ResettingPasswordController {
    private final UserService userService;
    private final CustomTokenService customTokenService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute User user) {
        String output = "";
        User user1 = userService.findByEmail(user.getEmail());
        if (user1 != null) {
            output = userService.sendEmail(user);
        }
        if (output.equals("success")) {
            return "redirect:/register?success";
        }
        return "redirect:/signIn?error";
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = customTokenService.findByToken(token);
        if (reset != null && userService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordProcess(@ModelAttribute User user) {
        userService.resetPasswordProcess(user);
        return "redirect:/signIn";
    }
}

package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.TokenRepository;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ResettingPasswordController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
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
        User user1 = userRepository.findByEmail(user.getEmail());
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
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && userService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword")
    public String passwordResetProcess(@ModelAttribute User user) {
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user1);
        }
        return "redirect:/signIn";
    }
}

package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ResettingPasswordController {
    private final UserSearchingService userSearchingService;
    private final UserVerificationService userVerificationService;
    private final UserModificationService userModificationService;
    private final CustomTokenService customTokenService;
    private final MailSendingService mailSendingService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return new ResponseEntity<>("forgotPassword", HttpStatus.OK).getBody();
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute User user) {
        String output = "";
        User user1 = userSearchingService.findByEmail(user.getEmail());
        if (user1 != null) {
            output = mailSendingService.sendEmail(user);
        }
        if (output.equals("success")) {
            return new ResponseEntity<>("redirect:/register?success", HttpStatus.OK).getBody();
        }
        return new ResponseEntity<>("redirect:/signIn?error", HttpStatus.OK).getBody();
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = customTokenService.findByToken(token);
        if (reset != null && userVerificationService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return new ResponseEntity<>("resetPassword", HttpStatus.OK).getBody();
        }
        return new ResponseEntity<>("redirect:/forgotPassword?error", HttpStatus.OK).getBody();
    }

    @PostMapping("/resetPassword")
    public String resetPasswordProcess(@ModelAttribute User user) {
        userModificationService.resetPasswordProcess(user);
        return new ResponseEntity<>("redirect:/signIn", HttpStatus.OK).getBody();
    }
}

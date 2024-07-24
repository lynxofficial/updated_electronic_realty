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
import ru.realty.erealty.service.custom.CustomTokenService;
import ru.realty.erealty.service.user.UserModificationService;
import ru.realty.erealty.service.user.UserSearchingService;
import ru.realty.erealty.service.user.UserVerificationService;
import ru.realty.erealty.service.mail.MailSendingService;
import ru.realty.erealty.service.common.CommonUserAuthorizationService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ResettingPasswordController {
    private final UserSearchingService userSearchingService;
    private final UserVerificationService userVerificationService;
    private final UserModificationService userModificationService;
    private final CustomTokenService customTokenService;
    private final MailSendingService mailSendingService;
    private final CommonUserAuthorizationService commonUserAuthorizationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        commonUserAuthorizationService.setCommonUser(principal, model);
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return new ResponseEntity<>("forgotPassword", HttpStatus.OK).getBody();
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(final @ModelAttribute User user) {
        User targetUser = userSearchingService.findByEmail(user.getEmail());
        if (targetUser != null && "success".equals(mailSendingService.sendEmail(user))) {
            return new ResponseEntity<>("redirect:/register?success", HttpStatus.OK).getBody();
        }
        return new ResponseEntity<>("redirect:/signIn?error", HttpStatus.OK).getBody();
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(final @PathVariable String token, final Model model) {
        PasswordResetToken reset = customTokenService.findByToken(token);
        if (reset != null && userVerificationService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return new ResponseEntity<>("resetPassword", HttpStatus.OK).getBody();
        }
        return new ResponseEntity<>("redirect:/forgotPassword?error", HttpStatus.OK).getBody();
    }

    @PostMapping("/resetPassword")
    public String resetPasswordProcess(final @ModelAttribute User user) {
        userModificationService.resetPasswordProcess(user);
        return new ResponseEntity<>("redirect:/signIn", HttpStatus.OK).getBody();
    }
}

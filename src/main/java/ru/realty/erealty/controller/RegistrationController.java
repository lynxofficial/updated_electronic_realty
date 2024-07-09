package ru.realty.erealty.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.CommonUserAuthorizationService;
import ru.realty.erealty.service.RegistrationTemplateFillingService;
import ru.realty.erealty.service.UserModificationService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserModificationService userModificationService;
    private final RegistrationTemplateFillingService registrationTemplateFillingService;
    private final CommonUserAuthorizationService commonUserAuthorizationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        commonUserAuthorizationService.setCommonUser(principal, model);
    }

    @PostMapping("/saveUser")
    public String saveUser(
            final @ModelAttribute User user,
            final HttpSession httpSession,
            final HttpServletRequest httpServletRequest
    ) {
        userModificationService.saveUser(user, httpSession, httpServletRequest);
        return new ResponseEntity<>("redirect:/register", HttpStatus.OK).getBody();
    }

    @GetMapping("/verify")
    public String verifyAccount(final @Param("code") String code, final Model model) {
        registrationTemplateFillingService.fillRegistrationTemplate(code, model);
        return new ResponseEntity<>("message", HttpStatus.OK).getBody();
    }
}

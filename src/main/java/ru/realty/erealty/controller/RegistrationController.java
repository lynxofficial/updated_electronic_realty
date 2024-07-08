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
import ru.realty.erealty.service.RegistrationTemplateFillingService;
import ru.realty.erealty.service.UserModificationService;
import ru.realty.erealty.service.UserSearchingService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserSearchingService userSearchingService;
    private final UserModificationService userModificationService;
    private final RegistrationTemplateFillingService registrationTemplateFillingService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
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

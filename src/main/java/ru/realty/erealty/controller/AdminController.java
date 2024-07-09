package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.realty.erealty.service.CommonUserAuthorizationService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final CommonUserAuthorizationService commonUserAuthorizationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        commonUserAuthorizationService.setCommonUser(principal, model);
    }

    @GetMapping("/profile")
    public String profile() {
        return new ResponseEntity<>("admin_profile", HttpStatus.OK).getBody();
    }
}

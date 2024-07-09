package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.realty.erealty.service.CommonUserAuthorizationService;
import ru.realty.erealty.service.HomeTemplateFillingService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeTemplateFillingService homeTemplateFillingService;
    private final CommonUserAuthorizationService commonUserAuthorizationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        commonUserAuthorizationService.setCommonUser(principal, model);
    }

    @GetMapping("/")
    public String index(final Model model) {
        homeTemplateFillingService.fillHomeTemplate(model);
        return new ResponseEntity<>("index", HttpStatus.OK).getBody();
    }

    @GetMapping("/register")
    public String register() {
        return new ResponseEntity<>("register", HttpStatus.OK).getBody();
    }

    @GetMapping("/signIn")
    public String login() {
        return new ResponseEntity<>("login", HttpStatus.OK).getBody();
    }
}

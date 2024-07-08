package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.realty.erealty.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.realty.erealty.service.UserSearchingService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserSearchingService userSearchingService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/profile")
    public String profile() {
        return new ResponseEntity<>("admin_profile", HttpStatus.OK).getBody();
    }
}

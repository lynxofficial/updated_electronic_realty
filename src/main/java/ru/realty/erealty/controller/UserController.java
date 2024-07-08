package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.realty.erealty.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.realty.erealty.service.UserModificationService;
import ru.realty.erealty.service.UserSearchingService;
import ru.realty.erealty.service.UserTemplateFillingService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserSearchingService userSearchingService;
    private final UserModificationService userModificationService;
    private final UserTemplateFillingService userTemplateFillingService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/user/profile")
    public String profile() {
        return new ResponseEntity<>("profile", HttpStatus.OK).getBody();
    }

    @GetMapping("/deleteUsers")
    public String deleteUsers(final Model model) {
        userTemplateFillingService.fillDeleteUserTemplate(model);
        return new ResponseEntity<>("deleteUsers", HttpStatus.OK).getBody();
    }

    @PostMapping("/deleteUser")
    public String deleteUser(final @RequestParam Integer userId) {
        userModificationService.deleteById(userId);
        return new ResponseEntity<>("redirect:/deleteUsers", HttpStatus.OK).getBody();
    }
}

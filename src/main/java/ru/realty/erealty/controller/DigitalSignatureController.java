package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.UserService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;

@Controller
@RequiredArgsConstructor
public class DigitalSignatureController {
    private final UserService userService;
    private final UserRepository userRepository;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/generateUserDigitalSignature")
    public String getGenerateUserDigitalSignature() {
        return "generateUserDigitalSignature";
    }

    @PostMapping("/generateUserDigitalSignature")
    public String generateUserDigitalSignature(@ModelAttribute User user) throws
            NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String digitalSignature = userService.generateDigitalSignature(user.getPasswordForDigitalSignature());
        user.setDigitalSignature(digitalSignature);
        userRepository.save(user);
        return "redirect:/user/profile";
    }
}

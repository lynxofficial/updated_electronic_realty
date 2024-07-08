package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.DigitalSignatureGenerationService;
import ru.realty.erealty.service.UserSearchingService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;

@Controller
@RequiredArgsConstructor
public class DigitalSignatureController {
    private final UserSearchingService userSearchingService;
    private final DigitalSignatureGenerationService digitalSignatureGenerationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/generateUserDigitalSignature")
    public String getGenerateUserDigitalSignature() {
        return new ResponseEntity<>("generateUserDigitalSignature", HttpStatus.OK).getBody();
    }

    @PostMapping("/generateUserDigitalSignature")
    public String generateUserDigitalSignature(final @ModelAttribute User user) throws
            NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        digitalSignatureGenerationService.generateDigitalSignature(user.getPasswordForDigitalSignature(), user);
        return new ResponseEntity<>("redirect:/user/profile", HttpStatus.OK).getBody();
    }
}

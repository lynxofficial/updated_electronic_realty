package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.service.RealtyObjectService;
import ru.realty.erealty.service.RealtyObjectTemplateFillingService;
import ru.realty.erealty.service.UserSearchingService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RealtyObjectController {
    private final UserSearchingService userSearchingService;
    private final RealtyObjectService realtyObjectService;
    private final RealtyObjectTemplateFillingService realtyObjectTemplateFillingService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userSearchingService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/realtyObject")
    public String getRealtyObject() {
        if (!(SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
            return new ResponseEntity<>("redirect:/signIn", HttpStatus.OK).getBody();
        }
        return new ResponseEntity<>("realtyObject", HttpStatus.OK).getBody();
    }

    @GetMapping("/myRealtyObjects")
    public String getRealtyObjects(Model model) {
        realtyObjectTemplateFillingService.fillRealtyObjectTemplate(model);
        return new ResponseEntity<>("myRealtyObjects", HttpStatus.OK).getBody();
    }

    @PostMapping("/sellRealtyObject")
    public String sellRealtyObject(@ModelAttribute RealtyObject realtyObject, @RequestParam("image") MultipartFile file,
                                   @Value("${default.image.path}") String defaultImagePath)
            throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userSearchingService.findByEmail(email);
        realtyObjectService.sellRealtyObject(user, realtyObject, file, defaultImagePath);
        return new ResponseEntity<>("redirect:/myRealtyObjects", HttpStatus.OK).getBody();
    }

    @GetMapping("/buyRealtyObject/{realtyObjectId}")
    public String buyRealtyObject(Model model, @PathVariable String realtyObjectId) throws RealtyObjectNotFoundException {
        realtyObjectTemplateFillingService.fillBuyRealtyObjectTemplate(model, realtyObjectId);
        return new ResponseEntity<>("buyRealtyObject", HttpStatus.OK).getBody();
    }

    @PostMapping("/buyRealtyObject")
    public String buyRealtyObjectWithDigitalSignature(@ModelAttribute RealtyObject realtyObject)
            throws RealtyObjectNotFoundException {
        return realtyObjectService.buyRealtyObjectWithDigitalSignature(realtyObject) ?
                new ResponseEntity<>("redirect:/", HttpStatus.OK).getBody()
                : new ResponseEntity<>("/errorWithDigitalSignatureOrLessBalance", HttpStatus.OK).getBody();
    }

    @GetMapping("/deleteRealtyObjects")
    public String deleteRealtyObjects(Model model) {
        realtyObjectTemplateFillingService.fillDeleteRealtyObjectsTemplate(model);
        return new ResponseEntity<>("/deleteRealtyObjects", HttpStatus.OK).getBody();
    }

    @PostMapping("/deleteRealtyObject")
    public String deleteRealtyObject(@ModelAttribute RealtyObject realtyObject) throws RealtyObjectNotFoundException {
        realtyObjectService.deleteRealtyObject(realtyObject);
        return new ResponseEntity<>("redirect:/deleteRealtyObjects", HttpStatus.OK).getBody();
    }
}

package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.service.RealtyObjectService;
import ru.realty.erealty.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RealtyObjectController {
    private final UserService userService;
    private final RealtyObjectService realtyObjectService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/realtyObject")
    public String getRealtyObject() {
        if (!(SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
            return "redirect:/signIn";
        }
        return "realtyObject";
    }

    @GetMapping("/myRealtyObjects")
    public String getRealtyObjects(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        List<RealtyObject> realtyObjects = realtyObjectService.findAllByUser(user);
        model.addAttribute("realtyObjects", realtyObjects);
        return "myRealtyObjects";
    }

    @PostMapping("/sellRealtyObject")
    public String sellRealtyObject(@ModelAttribute RealtyObject realtyObject, @RequestParam("image") MultipartFile file)
            throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        realtyObjectService.sellRealtyObject(user, realtyObject, file);
        return "redirect:/myRealtyObjects";
    }

    @GetMapping("/buyRealtyObject/{realtyObjectId}")
    public String buyRealtyObject(Model model, @PathVariable String realtyObjectId) {
        model.addAttribute("realtyObject", realtyObjectService.buyRealtyObject(realtyObjectId));
        return "buyRealtyObject";
    }

    @PostMapping("/buyRealtyObject")
    public String buyRealtyObjectWithDigitalSignature(@ModelAttribute RealtyObject realtyObject) {
        return realtyObjectService.buyRealtyObjectWithDigitalSignature(realtyObject) ? "redirect:/"
                : "/errorWithDigitalSignatureOrLessBalance";
    }

    @GetMapping("/deleteRealtyObjects")
    public String deleteRealtyObjects(Model model) {
        List<RealtyObject> realtyObjects = realtyObjectService.findAll();
        model.addAttribute("realtyObjects", realtyObjects);
        return "/deleteRealtyObjects";
    }

    @PostMapping("/deleteRealtyObject")
    public String deleteRealtyObject(@ModelAttribute RealtyObject realtyObject) {
        realtyObjectService.deleteRealtyObject(realtyObject);
        return "redirect:/deleteRealtyObjects";
    }
}

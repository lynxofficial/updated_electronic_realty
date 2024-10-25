package ru.realty.erealty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.service.common.CommonUserAuthorizationService;
import ru.realty.erealty.service.realty.RealtyObjectService;
import ru.realty.erealty.service.template.realty.RealtyObjectTemplateFillingService;
import ru.realty.erealty.service.user.UserSearchingService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RealtyObjectController {
    private final UserSearchingService userSearchingService;
    private final RealtyObjectService realtyObjectService;
    private final RealtyObjectTemplateFillingService realtyObjectTemplateFillingService;
    private final CommonUserAuthorizationService commonUserAuthorizationService;

    @ModelAttribute
    public void commonUser(final Principal principal, final Model model) {
        commonUserAuthorizationService.setCommonUser(principal, model);
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
    public String getRealtyObjects(final Model model) {
        realtyObjectTemplateFillingService.fillRealtyObjectTemplate(model);
        return new ResponseEntity<>("myRealtyObjects", HttpStatus.OK).getBody();
    }

    @PostMapping("/sellRealtyObject")
    public String sellRealtyObject(
            final @ModelAttribute RealtyObject realtyObject,
            final @RequestParam("image") MultipartFile file
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userSearchingService.findByEmail(email);
        realtyObjectService.sellRealtyObject(user, realtyObject, file);
        return new ResponseEntity<>("redirect:/myRealtyObjects", HttpStatus.OK).getBody();
    }

    @GetMapping("/buyRealtyObject/{id}")
    public String buyRealtyObject(final Model model, final @PathVariable String id)
            throws RealtyObjectNotFoundException {
        realtyObjectTemplateFillingService.fillBuyRealtyObjectTemplate(model, id);
        return new ResponseEntity<>("buyRealtyObject", HttpStatus.OK).getBody();
    }

    @PostMapping("/buyRealtyObject/{id}")
    public String buyRealtyObjectWithDigitalSignature(final @PathVariable Integer id)
            throws RealtyObjectNotFoundException {
        return realtyObjectService.buyRealtyObjectWithDigitalSignature(id)
                ? new ResponseEntity<>("redirect:/", HttpStatus.OK).getBody()
                : new ResponseEntity<>("/errorWithDigitalSignatureOrLessBalance", HttpStatus.OK).getBody();
    }

    @GetMapping("/deleteRealtyObjects")
    public String deleteRealtyObjects(final Model model) {
        realtyObjectTemplateFillingService.fillDeleteRealtyObjectsTemplate(model);
        return new ResponseEntity<>("/deleteRealtyObjects", HttpStatus.OK).getBody();
    }

    @PostMapping("/deleteRealtyObject")
    public String deleteRealtyObject(final @ModelAttribute RealtyObject realtyObject)
            throws RealtyObjectNotFoundException {
        realtyObjectService.deleteRealtyObject(realtyObject);
        return new ResponseEntity<>("redirect:/deleteRealtyObjects", HttpStatus.OK).getBody();
    }
}

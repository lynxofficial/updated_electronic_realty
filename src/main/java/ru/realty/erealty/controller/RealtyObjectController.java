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
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.RealtyObjectService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RealtyObjectController {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static";

    private final UserRepository userRepository;
    private final RealtyObjectRepository realtyObjectRepository;
    private final RealtyObjectService realtyObjectService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
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
        User user = userRepository.findByEmail(email);
        List<RealtyObject> realtyObjects = realtyObjectRepository.findAllByUser(user);
        model.addAttribute("realtyObjects", realtyObjects);
        return "myRealtyObjects";
    }

    @PostMapping("/sellRealtyObject")
    public String sellRealtyObject(@ModelAttribute RealtyObject realtyObject, @RequestParam("image") MultipartFile file)
            throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        realtyObject.setUser(user);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        realtyObject.setImageUrl(fileNameAndPath.getFileName().toString());
        if (realtyObject.getPrice() == null) {
            realtyObject.setPrice(BigDecimal.ZERO);
        }
        realtyObjectService.saveRealtyObject(realtyObject);
        return "redirect:/myRealtyObjects";
    }

    @GetMapping("/buyRealtyObject/{realtyObjectId}")
    public String buyRealtyObject(Model model, @PathVariable String realtyObjectId) {
        RealtyObject realtyObject = null;
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(Integer.valueOf(realtyObjectId));
        if (optionalRealtyObject.isPresent()) {
            realtyObject = optionalRealtyObject.get();
        }
        model.addAttribute("realtyObject", realtyObject);
        return "buyRealtyObject";
    }

    @PostMapping("/buyRealtyObject")
    public String buyRealtyObjectWithDigitalSignature(@ModelAttribute RealtyObject realtyObject) {
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(realtyObject.getRealtyObjectId());
        RealtyObject currentRealtyObject = null;
        if (optionalRealtyObject.isPresent()) {
            currentRealtyObject = optionalRealtyObject.get();
        }
        assert currentRealtyObject != null;
        Optional<User> optionalUser = userRepository.findById(currentRealtyObject.getUser().getUserId());
        User targetUser = null;
        if (optionalUser.isPresent()) {
            targetUser = optionalUser.get();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email);
        if (currentUser.getBalance() != null) {
            assert targetUser != null;
            if (currentUser.getBalance().subtract(currentRealtyObject.getPrice()).compareTo(BigDecimal.ZERO) < 0
                    || currentUser.getDigitalSignature() == null
                    || currentUser.getUserId().equals(targetUser.getUserId())) {
                return "/errorWithDigitalSignatureOrLessBalance";
            }
            currentUser.setBalance(currentUser.getBalance().subtract(currentRealtyObject.getPrice()));
            targetUser.setBalance(targetUser.getBalance().add(currentRealtyObject.getPrice()));
            targetUser.getRealtyObjects().remove(currentRealtyObject);
            currentRealtyObject.setUser(null);
            userRepository.save(currentUser);
            userRepository.save(targetUser);
            realtyObjectService.delete(currentRealtyObject);
        }
        return "redirect:/";
    }

    @GetMapping("/deleteRealtyObjects")
    public String deleteRealtyObjects(Model model) {
        List<RealtyObject> realtyObjects = realtyObjectRepository.findAll();
        model.addAttribute("realtyObjects", realtyObjects);
        return "/deleteRealtyObjects";
    }

    @PostMapping("/deleteRealtyObject")
    public String deleteRealtyObject(@ModelAttribute RealtyObject realtyObject) {
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(realtyObject.getRealtyObjectId());
        RealtyObject currentRealtyObject = null;
        if (optionalRealtyObject.isPresent()) {
            currentRealtyObject = optionalRealtyObject.get();
        }
        assert currentRealtyObject != null;
        User user = currentRealtyObject.getUser();
        assert user != null;
        user.getRealtyObjects().remove(currentRealtyObject);
        currentRealtyObject.setUser(null);
        realtyObjectService.delete(currentRealtyObject);
        return "redirect:/deleteRealtyObjects";
    }
}

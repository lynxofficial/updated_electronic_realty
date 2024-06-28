package ru.realty.erealty.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.TokenRepository;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.RealtyObjectService;
import ru.realty.erealty.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static";

    private final UserService userService;
    private final UserRepository userRepository;
    private final RealtyObjectService realtyObjectService;
    private final RealtyObjectRepository realtyObjectRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AgencyRepository agencyRepository;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        List<RealtyObject> realtyObjectList = realtyObjectRepository.findAll();
        model.addAttribute("realtyObjects", realtyObjectList);
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/signIn")
    public String login() {
        return "login";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURL().toString();
        url = url.replace(httpServletRequest.getServletPath(), "");
        user.setBalance(BigDecimal.ZERO);
        User user1 = userService.saveUser(user, url);
        if (user1 != null) {
            httpSession.setAttribute("msg", "Регистрация успешно выполнена!");
        } else {
            httpSession.setAttribute("msg", "Ошибка сервера");
        }
        return "redirect:/register";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        Boolean flag = userService.verifyAccount(code);
        if (flag) {
            model.addAttribute("msg", "Ваш аккаунт успешно подтвержден!");
        } else {
            model.addAttribute("msg", "Ваш верификационный код может быть некорректным или" +
                    " использованным!");
        }
        return "message";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute User user) {
        String output = "";
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            output = userService.sendEmail(user);
        }
        if (output.equals("success")) {
            return "redirect:/register?success";
        }
        return "redirect:/signIn?error";
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && userService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword")
    public String passwordResetProcess(@ModelAttribute User user) {
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user1);
        }
        return "redirect:/signIn";
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

    @GetMapping("/agencies")
    public String getAllAgencies(Model model) {
        List<Agency> agencies = agencyRepository.findAll();
        model.addAttribute("agencies", agencies);
        return "agencies";
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

    @GetMapping("/deleteUsers")
    public String deleteUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "deleteUsers";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer userId) {
        userService.deleteById(userId);
        return "redirect:/deleteUsers";
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

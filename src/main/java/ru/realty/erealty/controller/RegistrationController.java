package ru.realty.erealty.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    private final UserService userService;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
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
}

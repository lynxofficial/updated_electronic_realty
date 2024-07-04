package ru.realty.erealty.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserVerificationService, UserSearchingService, UserModificationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSendingService mailSendingService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> saveUser(
            User user,
            String url,
            @Value("${default.mail.image.path}") String defaultMailImagePath
    ) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);
        new Thread(() -> mailSendingService.sendEmail(savedUser, url, defaultMailImagePath)).start();
        return Optional.of(savedUser);
    }

    @Override
    public void saveUser(User user, HttpSession httpSession, HttpServletRequest httpServletRequest,
                         @Value("${default.mail.image.path}") String defaultMailImagePath) {
        String url = httpServletRequest.getRequestURL().toString();
        url = url.replace(httpServletRequest.getServletPath(), "");
        user.setBalance(BigDecimal.ZERO);
        Optional<User> savedUser = saveUser(user, url, defaultMailImagePath);
        savedUser.ifPresentOrElse(value -> httpSession.setAttribute("msg", "Регистрация успешно выполнена!"),
                () -> httpSession.setAttribute("msg", "Ошибка сервера"));
    }

    @Override
    public void removeSessionMessage() {
        HttpSession httpSession = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest().getSession();
        httpSession.removeAttribute("msg");
    }

    @Override
    public boolean hasExpired(LocalDateTime expiryDateTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(localDateTime);
    }

    @Override
    public Boolean verifyAccount(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        user.setEnable(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public void resetPasswordProcess(User user) {
        User currentUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
    }

    @Override
    public Boolean isNotPositiveBalanceOrExistsDigitalSignature(User currentUser, User targetUser,
                                                                RealtyObject currentRealtyObject) {
        return currentUser.getBalance().subtract(currentRealtyObject.getPrice()).compareTo(BigDecimal.ZERO) < 0
                || currentUser.getDigitalSignature() == null
                || currentUser.getUserId().equals(targetUser.getUserId());
    }
}

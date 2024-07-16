package ru.realty.erealty.service.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.realty.erealty.service.user.UserVerificationService;
import ru.realty.erealty.service.mail.MailSendingService;
import ru.realty.erealty.service.user.UserModificationService;
import ru.realty.erealty.service.user.UserSearchingService;

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
    public User findByEmail(final String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public void deleteById(final Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> saveUser(
            final User user,
            final String url
    ) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);
        new Thread(() -> mailSendingService.sendEmail(savedUser, url)).start();
        return Optional.of(savedUser);
    }

    @Override
    public void saveUser(
            final User user,
            final HttpSession httpSession,
            final HttpServletRequest httpServletRequest
    ) {
        String url = httpServletRequest.getRequestURL().toString();
        url = url.replace(httpServletRequest.getServletPath(), "");
        user.setBalance(BigDecimal.ZERO);
        Optional<User> savedUser = saveUser(user, url);
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
    public boolean hasExpired(final LocalDateTime expiryDateTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(localDateTime);
    }

    @Override
    public Boolean verifyAccount(final String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        user.setEnable(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public void resetPasswordProcess(final User user) {
        User currentUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
    }

    @Override
    public Boolean isNotPositiveBalanceOrExistsDigitalSignature(
            final User currentUser,
            final User targetUser,
            final RealtyObject currentRealtyObject
    ) {
        return currentUser.getBalance().subtract(currentRealtyObject.getPrice()).compareTo(BigDecimal.ZERO) < 0
                || currentUser.getDigitalSignature() == null
                || currentUser.getUserId().equals(targetUser.getUserId());
    }
}

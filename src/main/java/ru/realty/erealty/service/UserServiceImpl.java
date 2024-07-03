package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserVerificationService, UserSearchingService, UserModificationService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final CustomTokenRepository customTokenRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Optional<User> saveUser(User user, String url) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User user1 = userRepository.save(user);
        sendEmail(user1, url);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void saveUser(User user, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURL().toString();
        url = url.replace(httpServletRequest.getServletPath(), "");
        user.setBalance(BigDecimal.ZERO);
        Optional<User> user1 = saveUser(user, url);
        user1.ifPresentOrElse(value -> httpSession.setAttribute("msg", "Регистрация успешно выполнена!"),
                () -> httpSession.setAttribute("msg", "Ошибка сервера"));
    }

    @Override
    public void removeSessionMessage() {
        HttpSession httpSession = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest().getSession();
        httpSession.removeAttribute("msg");
    }

    @Override
    public void sendEmail(User user, String url) {
        String from = "tester17591@yandex.ru";
        String to = user.getEmail();
        String subject = "Подтверждение аккаунта";
        String content = "Дорогой [[name]],<br>" + "Пожалуйста, перейдите по ссылке для подтверждения аккаунта:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТЬ</a></h3>" + "Спасибо,<br>" + "Egor";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from, "Egor");
            helper.setTo(to);
            helper.setSubject(subject);
            content = content.replace("[[name]]", user.getFullName());
            String siteUrl = url + "/verify?code=" + user.getVerificationCode();
            System.out.println(siteUrl);
            content = content.replace("[[URL]]", siteUrl);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String sendEmail(User user) {
        try {
            User currentUser = userRepository
                    .findByEmail(user.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            String resetLink = generateResetToken(currentUser);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("tester17591@yandex.ru");
            simpleMailMessage.setTo(currentUser.getEmail());
            simpleMailMessage.setSubject("Сброс пароля");
            simpleMailMessage.setText("Здравствуйте \n\n" + "Пожалуйста, кликните на эту ссылку для сброса пароля:" +
                    resetLink + ". \n\n" + "С уважением \n" + "Egor");
            System.out.println(resetLink);
            javaMailSender.send(simpleMailMessage);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    private String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setUser(user);
        customTokenRepository.save(resetToken);
        String endpointUrl = "http://localhost:8080/resetPassword";
        return endpointUrl + "/" + resetToken.getToken();
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
    public void generateDigitalSignature(String passwordForDigitalSignature, User user) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256WithDSA");
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        signature.initSign(keyPair.getPrivate(), secureRandom);
        byte[] data = passwordForDigitalSignature.getBytes(StandardCharsets.UTF_8);
        signature.update(data);
        byte[] digitalSignature = signature.sign();
        user.setDigitalSignature(new String(digitalSignature, StandardCharsets.UTF_16));
        userRepository.save(user);
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

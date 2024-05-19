package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.TokenRepository;
import ru.realty.erealty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public User saveUser(User user, String url) {
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User user1 = userRepository.save(user);
        sendEmail(user1, url);
        return userRepository.save(user);
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
            User user1 = userRepository.findByEmail(user.getEmail());
            String resetLink = generateResetToken(user1);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("tester17591@yandex.ru");
            simpleMailMessage.setTo(user1.getEmail());
            simpleMailMessage.setSubject("Сброс пароля");
            simpleMailMessage.setText("Здравствуйте \n\n" + "Пожалуйста, кликните на эту ссылку для сброса пароля:" +
                    resetLink + ". \n\n" + "С уважением \n" + "Egor");
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
        PasswordResetToken token = tokenRepository.save(resetToken);
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
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null) {
            return false;
        } else {
            user.setEnable(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public String generateDigitalSignature(String passwordForDigitalSignature) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256WithDSA");
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        signature.initSign(keyPair.getPrivate(), secureRandom);
        byte[] data = passwordForDigitalSignature.getBytes(StandardCharsets.UTF_8);
        signature.update(data);
        byte[] digitalSignature = signature.sign();
        return new String(digitalSignature, StandardCharsets.UTF_16);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}

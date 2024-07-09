package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MailSendingServiceImpl implements MailSendingService {
    private final JavaMailSender javaMailSender;
    private final FileHandlingSystemService fileHandlingSystemService;
    private final UserRepository userRepository;
    private final ResetTokenGenerationService resetTokenGenerationService;

    @Override
    public void sendEmail(
            final User user,
            final String url
    ) {
        String from = "tester17591@yandex.ru";
        String to = user.getEmail();
        String subject = "Подтверждение аккаунта";
        String content = "Дорогой [[name]],<br>" + "Пожалуйста, перейдите по ссылке для подтверждения аккаунта:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТЬ</a></h3>" + "Спасибо,<br>" + "Egor";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from, "Egor");
            helper.setTo(to);
            helper.setSubject(subject);
            content = content.replace("[[name]]", user.getFullName());
            String siteUrl = url + "/verify?code=" + user.getVerificationCode();
            content = content.replace("[[URL]]", siteUrl);
            helper.setText(content, true);
            fileHandlingSystemService.runAsyncAttachImage(fileHandlingSystemService.attachImage(helper));
            new Thread(() -> javaMailSender.send(message)).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendEmail(final User user) {
        try {
            User currentUser = userRepository
                    .findByEmail(user.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            String resetLink = resetTokenGenerationService.generateResetToken(currentUser);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("tester17591@yandex.ru");
            simpleMailMessage.setTo(currentUser.getEmail());
            simpleMailMessage.setSubject("Сброс пароля");
            simpleMailMessage.setText("Здравствуйте \n\n" + "Пожалуйста, кликните на эту ссылку для сброса пароля:"
                    + resetLink + ". \n\n" + "С уважением \n" + "Egor");
            new Thread(() -> javaMailSender.send(simpleMailMessage)).start();
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

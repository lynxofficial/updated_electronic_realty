package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final UserAttachmentImageMailService userAttachmentImageMailService;
    private final UserRepository userRepository;
    private final ResetTokenGenerationService resetTokenGenerationService;

    @Override
    public void sendEmail(
            User user,
            String url,
            @Value("${default.mail.image.path}") String defaultMailImagePath
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
            System.out.println(siteUrl);
            content = content.replace("[[URL]]", siteUrl);
            helper.setText(content, true);
            userAttachmentImageMailService.attachImageToMail(helper, defaultMailImagePath);
            new Thread(() -> javaMailSender.send(message)).start();
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
            String resetLink = resetTokenGenerationService.generateResetToken(currentUser);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("tester17591@yandex.ru");
            simpleMailMessage.setTo(currentUser.getEmail());
            simpleMailMessage.setSubject("Сброс пароля");
            simpleMailMessage.setText("Здравствуйте \n\n" + "Пожалуйста, кликните на эту ссылку для сброса пароля:" +
                    resetLink + ". \n\n" + "С уважением \n" + "Egor");
            System.out.println(resetLink);
            new Thread(() -> javaMailSender.send(simpleMailMessage)).start();
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}

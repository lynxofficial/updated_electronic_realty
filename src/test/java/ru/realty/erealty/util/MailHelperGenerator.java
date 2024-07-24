package ru.realty.erealty.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.entity.User;

import java.io.UnsupportedEncodingException;

public class MailHelperGenerator {
    public static MimeMessageHelper generateMailHelper(
            final JavaMailSender javaMailSender,
            final User user,
            final String url
    ) throws MessagingException, UnsupportedEncodingException {
        String from = "tester17591@yandex.ru";
        String to = user.getEmail();
        String subject = "Подтверждение аккаунта";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from, "Egor");
        helper.setTo(to);
        helper.setSubject(subject);
        String content = "Дорогой [[name]],<br>" + "Пожалуйста, перейдите по ссылке для подтверждения аккаунта:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТЬ</a></h3>" + "Спасибо,<br>" + "Egor";
        content = content.replace("[[name]]", user.getFullName());
        String siteUrl = url + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", siteUrl);
        helper.setText(content, true);
        return helper;
    }
}

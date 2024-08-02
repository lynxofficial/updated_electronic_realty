package ru.realty.erealty.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.dto.SimpleMailMessageDto;
import ru.realty.erealty.entity.User;

import java.io.UnsupportedEncodingException;

public class MailDataProvider {
    public static MimeMessageHelperDto mimeMessageHelperDtoBuilder(
            final String email,
            final User user,
            final String url,
            final MimeMessage mimeMessage
    ) {
        String to = user.getEmail();
        String subject = "Подтверждение аккаунта";
        String content = "Дорогой [[name]],<br>" + "Пожалуйста, перейдите по ссылке для подтверждения аккаунта:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТЬ</a></h3>" + "Спасибо,<br>" + "Egor";
        content = content.replace("[[name]]", user.getFullName());
        String siteUrl = url + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", siteUrl);
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(email, "Egor");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return MimeMessageHelperDto.builder()
                .mimeMessageHelper(mimeMessageHelper)
                .mimeMessage(mimeMessage)
                .from(email)
                .to(to)
                .subject(subject)
                .content(content)
                .build();
    }

    public static SimpleMailMessageDto simpleMailMessageDtoBuilder(
            final String email,
            final User currentUser,
            final String resetLink) {
        return SimpleMailMessageDto.builder()
                .from(email)
                .to(new String[]{currentUser.getEmail()})
                .subject("Сброс пароля")
                .text("Здравствуйте \n\n" + "Пожалуйста, кликните на эту ссылку для сброса пароля:"
                        + resetLink + ". \n\n" + "С уважением \n" + "Egor")
                .build();
    }
}

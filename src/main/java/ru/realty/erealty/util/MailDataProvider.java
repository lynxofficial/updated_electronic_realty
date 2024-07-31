package ru.realty.erealty.util;

import jakarta.mail.internet.MimeMessage;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.dto.SimpleMailMessageDto;
import ru.realty.erealty.entity.User;

public class MailDataProvider {
    public static MimeMessageHelperDto.MimeMessageHelperDtoBuilder mimeMessageHelperDtoBuilder(
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
        return MimeMessageHelperDto.builder()
                .mimeMessage(mimeMessage)
                .from(email)
                .to(to)
                .subject(subject)
                .content(content);
    }

    public static SimpleMailMessageDto.SimpleMailMessageDtoBuilder simpleMailMessageDtoBuilder(
            final String email,
            final User currentUser,
            final String resetLink) {
        return SimpleMailMessageDto.builder()
                .from(email)
                .to(new String[]{currentUser.getEmail()})
                .subject("Сброс пароля")
                .text("Здравствуйте \n\n" + "Пожалуйста, кликните на эту ссылку для сброса пароля:"
                        + resetLink + ". \n\n" + "С уважением \n" + "Egor");
    }
}

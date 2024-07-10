package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.entity.User;

class MailSendingServiceImplTest extends BaseSpringBootTest {
    @SneakyThrows
    @Test
    void sendEmailWithUrlThrowsException() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFullName("Test test test");
        user.setVerificationCode("12345");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String from = "tester17591@yandex.ru";
        String subject = "Подтверждение аккаунта";
        String content = "Дорогой [[name]],<br>" + "Пожалуйста, перейдите по ссылке для подтверждения аккаунта:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТЬ</a></h3>" + "Спасибо,<br>" + "Egor";
        String to = user.getEmail();
        helper.setFrom(from, "Egor");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        Mockito.doNothing()
                .when(mimeMessageHelper)
                .setTo(to);
        Mockito.doNothing()
                .when(mimeMessageHelper)
                .setFrom(from);
        String url = "test.com";
        Assertions.assertThrows(RuntimeException.class, () -> mailSendingServiceImpl.sendEmail(user, url));
    }
}

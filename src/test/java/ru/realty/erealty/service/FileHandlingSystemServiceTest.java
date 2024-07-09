package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FileHandlingSystemServiceTest {

    @MockBean
    protected UserDownloadingFileHttpResponseService userDownloadingFileHttpResponseService;
    @MockBean
    protected FileWritingService fileWritingService;
    @Autowired
    protected FileHandlingSystemService fileHandlingSystemService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    @SneakyThrows
    void attachImageShouldWork() {
//        precondition ----
//      prepare mocks
        var file = new File("src/main/resources/images/image610743684540901600tmp.png");
        Mockito.when(userDownloadingFileHttpResponseService.downloadFileHttpResponse(any()))
                .thenReturn(file);
        Mockito.doNothing()
                .when(fileWritingService)
                .writeFile(any());

//      prepare test data
        String from = "tester17591@yandex.ru";
        String to = "abc@mail.ru";
        String subject = "Подтверждение аккаунта";
        String content = "Дорогой [[name]],<br>" + "Пожалуйста, перейдите по ссылке для подтверждения аккаунта:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТЬ</a></h3>" + "Спасибо,<br>" + "Egor";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from, "Egor");
        helper.setTo(to);
        helper.setSubject(subject);
        content = content.replace("[[name]]", "Vasya");
        String siteUrl = "bestsite" + "/verify?code=" + "12345";
        content = content.replace("[[URL]]", siteUrl);
        helper.setText(content, true);
//        ------

//      real service call
        fileHandlingSystemService.attachImage(helper);

//      verification
        Awaitility.await().atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> {
                    Mockito.verify(fileHandlingSystemService).attachImage(helper);
                    Mockito.verify(fileWritingService).writeFile(file);
                });
    }
}
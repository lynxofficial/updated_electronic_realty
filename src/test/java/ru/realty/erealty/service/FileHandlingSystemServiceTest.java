package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;

class FileHandlingSystemServiceTest extends BaseSpringBootTest {
    @Test
    @SneakyThrows
    void attachImageShouldWork() {
//        precondition ----
//      prepare mocks
        var file = new File("src/main/resources/images/image610743684540901600tmp.png");
        Mockito.when(userDownloadingFileHttpResponseService.downloadFileHttpResponse(any()))
                .thenReturn(file);

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
                    fileHandlingSystemService.attachImage(helper);
                    fileWritingService.writeFile(file);
                });
        List<CompletableFuture<String>> completableFutures = fileHandlingSystemService.attachImage(helper);
        Assertions.assertNotNull(completableFutures);
    }

    @SneakyThrows
    @Test
    void runAsyncAttachImageShouldWork() {
        var file = new File("src/main/resources/images/image610743684540901600tmp.png");
        Mockito.when(userDownloadingFileHttpResponseService.downloadFileHttpResponse(any()))
                .thenReturn(file);
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
        List<CompletableFuture<String>> completableFutures = fileHandlingSystemService.attachImage(helper);
        Awaitility.await().atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> fileHandlingSystemService.runAsyncAttachImage(completableFutures));
    }
}

package ru.realty.erealty.service;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.MailHelperGenerator;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class FileHandlingSystemServiceTest extends BaseSpringBootTest {
    @Test
    @SneakyThrows
    void attachImageShouldWork() {
//        precondition ----
//      prepare mocks
        var file = new File("src/main/resources/images/image610743684540901600tmp.png");

//      prepare test data
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        User user = new User();
        String url = "test-url.com";
        user.setEmail("test@test.com");
        user.setFullName("test test test");
        user.setVerificationCode("1234");
        MailHelperGenerator.generateMailHelper(javaMailSender, user, url);
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
        User user = new User();
        String url = "test-url.com";
        user.setEmail("test@test.com");
        user.setFullName("test test test");
        user.setVerificationCode("1234");
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, url);
        List<CompletableFuture<String>> completableFutures = fileHandlingSystemService.attachImage(mimeMessageHelper);
        Awaitility.await().atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> fileHandlingSystemService.runAsyncAttachImage(completableFutures));
    }
}

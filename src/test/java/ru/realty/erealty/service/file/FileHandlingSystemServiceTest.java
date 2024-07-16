package ru.realty.erealty.service.file;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.MailHelperGenerator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class FileHandlingSystemServiceTest extends BaseSpringBootTest {
    @Test
    void attachImageShouldWork() throws MessagingException, UnsupportedEncodingException {
//        precondition ----
//      prepare mocks
        var file = new File("src/main/resources/images/imageForTest.png");

//      prepare test data
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
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

    @Test
    void attachImageThrowsException() throws MessagingException, UnsupportedEncodingException {
        var file = new File("src/main/resources/images/imageForTest.png");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
        MailHelperGenerator.generateMailHelper(javaMailSender, user, url);

        fileHandlingSystemService.attachImage(helper);

        Assertions.assertThrows(NullPointerException.class, () -> Awaitility.await().atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> {
                    fileHandlingSystemService.attachImage(null);
                    fileWritingService.writeFile(file);
                }));
    }

    @Test
    void runAsyncAttachImageShouldWork() throws MessagingException, UnsupportedEncodingException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, url);
        List<CompletableFuture<String>> completableFutures = fileHandlingSystemService.attachImage(mimeMessageHelper);

        Awaitility.await().atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> fileHandlingSystemService.runAsyncAttachImage(completableFutures));
    }

    @Test
    void runAsyncAttachImageThrowsException() throws MessagingException, UnsupportedEncodingException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, url);
        List<CompletableFuture<String>> completableFutures = fileHandlingSystemService.attachImage(mimeMessageHelper);

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> Awaitility.await().atMost(Duration.ofSeconds(5L))
                        .untilAsserted(() -> {
                            completableFutures.add(new CompletableFuture<>());
                            fileHandlingSystemService.runAsyncAttachImage(completableFutures);
                        }));
    }
}

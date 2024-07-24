package ru.realty.erealty.service.file;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.MailHelperGenerator;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

class FileHandlingSystemServiceTest extends BaseSpringBootTest {
    @Test
    void attachImageShouldWork() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
        MailHelperGenerator.generateMailHelper(javaMailSender, user, url);

        fileHandlingSystemService.attachImage(helper);

        Awaitility.await().atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> fileHandlingSystemService.attachImage(helper));

        List<CompletableFuture<byte[]>> completableFutures = fileHandlingSystemService.attachImage(helper);

        assertThat(completableFutures)
                .isNotNull();
    }

    @Test
    void attachImageThrowsException() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
        MailHelperGenerator.generateMailHelper(javaMailSender, user, url);

        fileHandlingSystemService.attachImage(helper);

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Awaitility.await().atMost(Duration.ofSeconds(5L))
                        .untilAsserted(() -> fileHandlingSystemService.attachImage(null)));
    }

    @Test
    void runAsyncAttachImageShouldWork() throws MessagingException, UnsupportedEncodingException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("1234")
                .build();
        String url = "test-url.com";
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, url);
        List<CompletableFuture<byte[]>> completableFutures = fileHandlingSystemService.attachImage(mimeMessageHelper);

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
        List<CompletableFuture<byte[]>> completableFutures = fileHandlingSystemService.attachImage(mimeMessageHelper);

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> Awaitility.await().atMost(Duration.ofSeconds(5L))
                        .untilAsserted(() -> {
                            completableFutures.add(new CompletableFuture<>());
                            fileHandlingSystemService.runAsyncAttachImage(completableFutures);
                        }));
    }
}

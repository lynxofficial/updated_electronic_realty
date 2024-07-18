package ru.realty.erealty.service.future;

import jakarta.mail.MessagingException;
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
import java.util.concurrent.CompletableFuture;

class PreparingCompletableFutureAttachmentServiceTest extends BaseSpringBootTest {
    @Test
    void prepareCompletableFutureAttachmentShouldWork() throws MessagingException, UnsupportedEncodingException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .build();
        String url = "https://raw.githubusercontent.com/lynxofficial/electronic_realty/main/images"
                + "/firstImageForDownloadingHttpRequest.png";
        CompletableFuture<String> completableFuture = fileHandlingHttpResponseService.attachImage(url);
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, url);

        Awaitility.await()
                .atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> preparingCompletableFutureAttachmentService
                        .prepareCompletableFutureAttachment(completableFuture, mimeMessageHelper));
    }

    @Test
    void prepareCompletableFutureAttachmentThrowsException() throws MessagingException, UnsupportedEncodingException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .build();
        String url = "https://raw.githubusercontent.com/lynxofficial/electronic_realty/main/images";
        CompletableFuture<String> completableFuture = fileHandlingHttpResponseService.attachImage(url);
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, null);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> Awaitility.await()
                        .atMost(Duration.ofSeconds(1L))
                        .untilAsserted(() -> preparingCompletableFutureAttachmentService
                                .prepareCompletableFutureAttachment(completableFuture, mimeMessageHelper)));
    }
}

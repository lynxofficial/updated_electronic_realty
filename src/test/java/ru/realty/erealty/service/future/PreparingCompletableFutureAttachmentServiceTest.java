package ru.realty.erealty.service.future;

import jakarta.mail.MessagingException;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;
import ru.realty.erealty.util.MailHelperGenerator;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class PreparingCompletableFutureAttachmentServiceTest extends BaseSpringBootTest {
    @Value("${default.image.links}")
    private List<String> imageLinks;

    @Test
    void prepareCompletableFutureAttachmentShouldWork() throws MessagingException, UnsupportedEncodingException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .build();
        String url = imageLinks.getFirst();
        CompletableFuture<byte[]> completableFuture = fileHandlingHttpResponseService.attachImage(url);
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
        String url = imageLinks.getFirst();
        CompletableFuture<byte[]> completableFuture = fileHandlingHttpResponseService.attachImage(url);
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, null);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Awaitility.await()
                        .atMost(Duration.ofNanos(1L))
                        .untilAsserted(() -> preparingCompletableFutureAttachmentService
                                .prepareCompletableFutureAttachment(completableFuture, mimeMessageHelper)));
    }
}

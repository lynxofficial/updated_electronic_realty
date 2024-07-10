package ru.realty.erealty.service;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.MailHelperGenerator;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

class PreparingCompletableFutureAttachmentServiceTest extends BaseSpringBootTest {
    @Test
    void prepareCompletableFutureAttachmentShouldWork() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFullName("test test test");
        String url = "https://habrastorage.org/r/w1560/getpro/habr/upload_files/0b7/801/68f"
                + "/0b780168f9aca7ced566032c14ebe23f.png";
        CompletableFuture<String> completableFuture = fileHandlingHttpResponseService.attachImage(url);
        MimeMessageHelper mimeMessageHelper = MailHelperGenerator.generateMailHelper(javaMailSender, user, url);
        Awaitility.await()
                .atMost(Duration.ofSeconds(5L))
                .untilAsserted(() -> preparingCompletableFutureAttachmentService
                        .prepareCompletableFutureAttachment(completableFuture, mimeMessageHelper));
    }
}

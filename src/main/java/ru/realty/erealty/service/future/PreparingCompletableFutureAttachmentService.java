package ru.realty.erealty.service.future;

import jakarta.mail.MessagingException;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@NoArgsConstructor
public class PreparingCompletableFutureAttachmentService {
    public void prepareCompletableFutureAttachment(
            final CompletableFuture<byte[]> completableFuture,
            final MimeMessageHelper messageHelper
    ) {
        try {
            messageHelper.addAttachment("image.png", new ByteArrayResource(completableFuture.get()));
        } catch (InterruptedException | ExecutionException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

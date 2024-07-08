package ru.realty.erealty.service;

import jakarta.mail.MessagingException;
import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@NoArgsConstructor
public class PreparingCompletableFutureAttachmentService {
    public void prepareCompletableFutureAttachment(
            final CompletableFuture<String> completableFuture,
            final MimeMessageHelper messageHelper
    ) {
        String value;
        try {
            value = completableFuture.get();
            messageHelper.addAttachment(value.substring(value.lastIndexOf('\\')),
                    new File(value));
        } catch (InterruptedException | ExecutionException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

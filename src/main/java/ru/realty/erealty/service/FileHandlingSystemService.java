package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileHandlingSystemService implements ImageHandlingService<List<CompletableFuture<String>>,
        MimeMessageHelper> {
    @Value("${default.image.links}")
    private final List<String> imageLinks;
    private final FileHandlingHttpResponseService fileHandlingHttpResponseService;
    private final PreparingCompletableFutureAttachmentService preparingCompletableFutureAttachmentService;

    @Override
    public List<CompletableFuture<String>> attachImage(final MimeMessageHelper messageHelper) {
        return imageLinks.stream()
                .map(fileHandlingHttpResponseService::attachImage)
                .peek(completableFuture -> preparingCompletableFutureAttachmentService
                        .prepareCompletableFutureAttachment(completableFuture, messageHelper))
                .toList();
    }

    public void runAsyncAttachImage(final List<CompletableFuture<String>> completableFutures) {
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new)).join();
    }
}

package ru.realty.erealty.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.realty.erealty.service.image.ImageHandlingService;
import ru.realty.erealty.service.future.PreparingCompletableFutureAttachmentService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileHandlingSystemService implements ImageHandlingService<List<CompletableFuture<byte[]>>,
        MimeMessageHelper> {
    @Value("${default.image.links}")
    private final List<String> imageLinks;
    private final FileHandlingHttpResponseService fileHandlingHttpResponseService;
    private final PreparingCompletableFutureAttachmentService preparingCompletableFutureAttachmentService;

    @Override
    public List<CompletableFuture<byte[]>> attachImage(final MimeMessageHelper messageHelper) {
        return imageLinks.stream()
                .map(imageLink -> {
                    CompletableFuture<byte[]> completableFuture = fileHandlingHttpResponseService
                            .attachImage(imageLink);
                    preparingCompletableFutureAttachmentService
                            .prepareCompletableFutureAttachment(completableFuture, messageHelper, imageLink);
                    return completableFuture;
                })
                .toList();
    }

    public void runAsyncAttachImage(final List<CompletableFuture<byte[]>> completableFutures) {
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new)).join();
    }
}

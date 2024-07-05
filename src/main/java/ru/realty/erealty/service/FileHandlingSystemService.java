package ru.realty.erealty.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FileHandlingSystemService implements ImageHandlingService<Void, MimeMessageHelper> {
    @Value("${default.image.links}")
    private final List<String> DEFAULT_IMAGE_LINKS;
    private final FileHandlingHttpResponseService fileHandlingHttpResponseService;

    @Override
    public Void attachImage(MimeMessageHelper messageHelper) {
        List<CompletableFuture<String>> completableFutures = DEFAULT_IMAGE_LINKS.stream()
                .map(fileHandlingHttpResponseService::attachImage)
                .peek(completableFuture -> {
                    String value;
                    try {
                        value = completableFuture.get();
                        messageHelper.addAttachment(value.substring(value.lastIndexOf('\\')),
                                new File(value));
                    } catch (InterruptedException | ExecutionException | MessagingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        return null;
    }
}

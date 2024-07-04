package ru.realty.erealty.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FileHandlingAndAttachmentHttpService {
    @Value("${default.image.links}")
    private final List<String> DEFAULT_IMAGE_LINKS;
    private final FileWritingService fileWritingService;
    private final UserDownloadingFileHttpResponseService userDownloadingFileHttpResponseService;

    public CompletableFuture<String> downloadImage(
            String imageLink,
            @Value("${default.mail.image.path}") String defaultMailImagePath
    ) {
        return CompletableFuture.supplyAsync(() -> {
            File file = userDownloadingFileHttpResponseService.downloadFileHttpResponse(imageLink);
            try {
                fileWritingService.writeFile(file, defaultMailImagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            assert file != null;
            return defaultMailImagePath + "\\" + file.getName() + ".png";
        });
    }

    public void attachImageToMail(MimeMessageHelper mimeMessageHelper,
                                  @Value("${default.mail.image.path}") String defaultMailImagePath) {
        List<CompletableFuture<String>> completableFutures = DEFAULT_IMAGE_LINKS.stream()
                .map(imageLink -> downloadImage(imageLink, defaultMailImagePath))
                .peek(completableFuture -> {
                    String value;
                    try {
                        value = completableFuture.get();
                        mimeMessageHelper.addAttachment(value.substring(value.lastIndexOf('\\')),
                                new File(value));
                    } catch (InterruptedException | ExecutionException | MessagingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
    }
}

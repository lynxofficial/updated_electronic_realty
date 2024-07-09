package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileHandlingHttpResponseService implements ImageHandlingService<CompletableFuture<String>, String> {
    private final UserDownloadingFileHttpResponseService userDownloadingFileHttpResponseService;
    private final FileWritingService fileWritingService;

    @Value("${default.mail.image.path}")
    private String defaultMailImagePath;

    @Override
    public CompletableFuture<String> attachImage(final String messageHelper) {
        return CompletableFuture.supplyAsync(() -> {
            File file = userDownloadingFileHttpResponseService.downloadFileHttpResponse(messageHelper);
            try {
                fileWritingService.writeFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            assert file != null;
            return defaultMailImagePath + "\\" + file.getName() + ".png";
        });
    }
}

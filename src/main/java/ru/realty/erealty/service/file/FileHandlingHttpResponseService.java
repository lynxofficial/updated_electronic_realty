package ru.realty.erealty.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.realty.erealty.service.image.ImageHandlingService;
import ru.realty.erealty.service.user.UserDownloadingFileHttpResponseService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileHandlingHttpResponseService implements ImageHandlingService<CompletableFuture<byte[]>, String> {
    private final UserDownloadingFileHttpResponseService userDownloadingFileHttpResponseService;

    @Override
    public CompletableFuture<byte[]> attachImage(final String messageHelper) {
        return CompletableFuture
                .supplyAsync(() -> userDownloadingFileHttpResponseService.downloadFileHttpResponse(messageHelper));
    }
}

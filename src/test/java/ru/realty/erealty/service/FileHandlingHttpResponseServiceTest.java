package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.concurrent.CompletableFuture;

class FileHandlingHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    @SneakyThrows
    void attachImageShouldWork() {
        String imageLink = "https://habrastorage.org/r/w1560/getpro/habr/upload_files/0b7/801/68f"
                + "/0b780168f9aca7ced566032c14ebe23f.png";
        Mockito.when(userDownloadingFileHttpResponseService.downloadFileHttpResponse(imageLink))
                .thenReturn(new File("\\image123tmp"));
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");
        Assertions.assertNotNull(actualCompletableFuture);
    }
}

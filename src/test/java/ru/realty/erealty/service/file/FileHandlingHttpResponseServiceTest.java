package ru.realty.erealty.service.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class FileHandlingHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    void attachImageShouldWork() {
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");

        Assertions.assertNotNull(actualCompletableFuture);
    }

    @Test
    void attachImageThrowsException() {
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");

        Assertions.assertThrows(ExecutionException.class, actualCompletableFuture::get);
    }
}

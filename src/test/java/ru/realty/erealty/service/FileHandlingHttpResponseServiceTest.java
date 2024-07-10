package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

class FileHandlingHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    @SneakyThrows
    void attachImageShouldWork() {
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");
        Assertions.assertNotNull(actualCompletableFuture);
    }
}

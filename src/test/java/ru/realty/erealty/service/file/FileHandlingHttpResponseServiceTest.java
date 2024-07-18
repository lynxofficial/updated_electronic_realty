package ru.realty.erealty.service.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class FileHandlingHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    void attachImageShouldWork() {
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");

        Assertions.assertThat(actualCompletableFuture)
                .isNotNull();
    }

    @Test
    void attachImageThrowsAssertionError() {

    }

    @Test
    void attachImageThrowsException() {
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");

        Assertions.assertThatExceptionOfType(ExecutionException.class)
                .isThrownBy(actualCompletableFuture::get);
    }
}

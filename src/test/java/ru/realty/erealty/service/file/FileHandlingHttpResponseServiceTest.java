package ru.realty.erealty.service.file;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FileHandlingHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    void attachImageShouldWork() {
        CompletableFuture<byte[]> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");

        assertThat(actualCompletableFuture)
                .isNotNull();
    }

    @Test
    void attachImageThrowsException() {
        CompletableFuture<byte[]> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");

        assertThatExceptionOfType(ExecutionException.class)
                .isThrownBy(actualCompletableFuture::get);
    }
}

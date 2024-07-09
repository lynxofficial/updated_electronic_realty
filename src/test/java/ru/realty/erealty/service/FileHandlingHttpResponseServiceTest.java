package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class FileHandlingHttpResponseServiceTest {
    @InjectMocks
    private FileHandlingHttpResponseService fileHandlingHttpResponseService;

    @Test
    public void attachImageTest() {
        CompletableFuture<String> actualCompletableFuture = fileHandlingHttpResponseService
                .attachImage("link");
        Assertions.assertNotNull(actualCompletableFuture);
    }
}

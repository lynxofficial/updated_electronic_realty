package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileHandlingHttpServiceImpl implements FileHandlingHttpService {
    private final FileWritingService fileWritingService;
    private final RestTemplate restTemplate;

    @Override
    public CompletableFuture<String> downloadImage(
            String imageLink,
            @Value("${default.mail.image.path}") String defaultMailImagePath,
            Integer imageNumber
    ) {
        return CompletableFuture.supplyAsync(() -> {
            File file = restTemplate.execute(imageLink, HttpMethod.GET, null, clientHttpResponse -> {
                File temporaryFile = File.createTempFile("image", "tmp");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(temporaryFile));
                return temporaryFile;
            });
            try {
                fileWritingService.writeFile(file, defaultMailImagePath, imageNumber);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return defaultMailImagePath + "/image" + imageNumber + ".png";
        });
    }
}

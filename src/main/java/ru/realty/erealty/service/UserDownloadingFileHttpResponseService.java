package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
public class UserDownloadingFileHttpResponseService {
    private final RestTemplate restTemplate;

    public File downloadFileHttpResponse(String imageLink) {
        return restTemplate.execute(imageLink, HttpMethod.GET, null, clientHttpResponse -> {
            File temporaryFile = File.createTempFile("image", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(temporaryFile));
            return temporaryFile;
        });
    }
}

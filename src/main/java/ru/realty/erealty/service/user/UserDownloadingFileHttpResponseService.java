package ru.realty.erealty.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserDownloadingFileHttpResponseService {
    private final WebClient webClient;

    public byte[] downloadFileHttpResponse(final String imageLink) {
        return webClient.get()
                .uri(imageLink)
                .accept(MediaType.IMAGE_JPEG)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}

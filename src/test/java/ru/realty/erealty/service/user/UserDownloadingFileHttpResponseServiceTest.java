package ru.realty.erealty.service.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;

import java.util.List;

class UserDownloadingFileHttpResponseServiceTest extends BaseSpringBootTest {
    @Value("${default.image.links}")
    private List<String> imageLinks;

    @Test
    void downloadFileHttpResponseShouldWork() {
        String firstImageLink = imageLinks.getFirst();

        Assertions.assertThat(userDownloadingFileHttpResponseService.downloadFileHttpResponse(firstImageLink))
                .isNotNull();
    }

    @Test
    void downloadFileHttpResponseThrowsException() {
        Assertions.assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> userDownloadingFileHttpResponseService.downloadFileHttpResponse(null));
    }
}

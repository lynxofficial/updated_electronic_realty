package ru.realty.erealty.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.realty.erealty.support.BaseSpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserDownloadingFileHttpResponseServiceTest extends BaseSpringBootTest {
    @Value("${default.image.links}")
    private List<String> imageLinks;

    @Test
    void downloadFileHttpResponseShouldWork() {
        String firstImageLink = imageLinks.getFirst();

        assertThat(userDownloadingFileHttpResponseService.downloadFileHttpResponse(firstImageLink))
                .isNotNull();
    }

    @Test
    void downloadFileHttpResponseThrowsException() {
        assertThatExceptionOfType(WebClientRequestException.class)
                .isThrownBy(() -> userDownloadingFileHttpResponseService.downloadFileHttpResponse(null));
    }
}

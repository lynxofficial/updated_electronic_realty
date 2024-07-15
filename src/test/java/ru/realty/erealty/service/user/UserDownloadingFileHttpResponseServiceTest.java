package ru.realty.erealty.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;

class UserDownloadingFileHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    void downloadFileHttpResponseShouldWork() {
        String imageLink = "https://raw.githubusercontent.com/lynxofficial/electronic_realty/main/images"
                + "/firstImageForDownloadingHttpRequest.png";

        Assertions.assertNotNull(userDownloadingFileHttpResponseService.downloadFileHttpResponse(imageLink));
    }

    @Test
    void downloadFileHttpResponseThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userDownloadingFileHttpResponseService.downloadFileHttpResponse(null));
    }
}

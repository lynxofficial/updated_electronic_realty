package ru.realty.erealty.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDownloadingFileHttpResponseServiceTest extends BaseSpringBootTest {
    @Test
    void downloadFileHttpResponseShouldWork() {
        String imageLink = "https://habrastorage.org/r/w1560/getpro/habr/upload_files/0b7/801/68f"
                + "/0b780168f9aca7ced566032c14ebe23f.png";
        Assertions.assertNotNull(userDownloadingFileHttpResponseService.downloadFileHttpResponse(imageLink));
    }
}

package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class FileWritingServiceImplTest extends BaseSpringBootTest {
    @SneakyThrows
    @Test
    void writeFileShouldWork() {
        File file = new File("src/main/resources/images/image610743684540901600tmp.png");
        fileWritingService.writeFile(file);
        File actualFile = new File("src/main/resources/images/image610743684540901600tmp.png.png");
        Assertions.assertNotNull(actualFile);
    }
}

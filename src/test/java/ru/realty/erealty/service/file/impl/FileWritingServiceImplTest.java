package ru.realty.erealty.service.file.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.realty.erealty.support.BaseSpringBootTest;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

class FileWritingServiceImplTest extends BaseSpringBootTest {
    @Test
    void writeFileShouldWork() throws IOException {
        File file = new File("src/main/resources/images/imageForTest.png");

        fileWritingService.writeFile(file);

        File actualFile = new File("src/main/resources/images/imageForTest.png.png");
        Assertions.assertNotNull(actualFile);
    }

    @Test
    void writeFileThrowsException() {
        File file = new File("default/image.png");

        Assertions.assertThrows(IIOException.class, () -> fileWritingService.writeFile(file));
    }
}

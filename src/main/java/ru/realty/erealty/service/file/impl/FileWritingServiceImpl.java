package ru.realty.erealty.service.file.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.realty.erealty.service.file.FileWritingService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileWritingServiceImpl implements FileWritingService {
    @Value("${default.mail.image.path}")
    private String defaultMailImagePath;

    @Override
    public void writeFile(final File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        File outputFile = new File(defaultMailImagePath + "\\" + file.getName() + ".png");
        ImageIO.write(bufferedImage, "png", outputFile);
    }
}

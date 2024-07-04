package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileWritingServiceImpl implements FileWritingService {
    @Override
    public void writeFile(
            File file,
            @Value("${default.mail.image.path}") String defaultMailImagePath
    ) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        File outputFile = new File(defaultMailImagePath + "\\" + file.getName() + ".png");
        ImageIO.write(bufferedImage, "png", outputFile);
    }
}

package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
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
            String defaultMailImagePath,
            Integer imageNumber
    ) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        ImageIO.write(bufferedImage, "png", file);
    }
}

package ru.realty.erealty.service;

import jakarta.mail.MessagingException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@NoArgsConstructor
public class UserAttachmentImageMailServiceImpl implements UserAttachmentImageMailService {
    private final List<String> DEFAULT_IMAGE_LINKS = List.of("https://habrastorage.org/r/w1560/getpro/habr" +
                    "/upload_files/0b7/801/68f/0b780168f9aca7ced566032c14ebe23f.png",
            "https://ultravds.com/wp-content/uploads/2023/10/756533742439601.jpg",
            "https://blog.skillfactory.ru/wp-content/uploads/2023/02/java1-1.png",
            "https://static.tildacdn.com/tild3232-6161-4564-b832-633763393539/CHemu-dolzhny-nauchi.jpg",
            "https://248006.selcdn.ru/main/upload/setka_images" +
                    "/15014919032020_e3ea06ecc4efe66fd609360c227a5daace25eda6.png");

    @Override
    public void attachImageToMail(MimeMessageHelper mimeMessageHelper,
                                  @Value("${default.mail.image.path}") String defaultMailImagePath) {
        List<CompletableFuture<String>> completableFutures = DEFAULT_IMAGE_LINKS.stream()
                .map(imageLink -> downloadImage(imageLink, defaultMailImagePath,
                        DEFAULT_IMAGE_LINKS.indexOf(imageLink)))
                .toList();
        completableFutures.stream()
                .map(value -> {
                    try {
                        return value.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(value -> {
                    try {
                        mimeMessageHelper.addAttachment(value, new File(defaultMailImagePath +
                                value.substring(value.lastIndexOf('/'))));
                        return value;
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
    }

    public CompletableFuture<String> downloadImage(String imageLink,
                                                   @Value("${default.mail.image.path}") String defaultMailImagePath,
                                                   Integer imageNumber) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL imageUrl = new URL(imageLink);
                BufferedImage bufferedImage = ImageIO.read(imageUrl);
                File file = new File(defaultMailImagePath + "/image" + imageNumber + ".png");
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return defaultMailImagePath + "/image" + imageNumber + ".png";
        });
    }
}

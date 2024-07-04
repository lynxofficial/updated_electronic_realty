package ru.realty.erealty.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserAttachmentImageMailServiceImpl implements UserAttachmentImageMailService {
    private final List<String> DEFAULT_IMAGE_LINKS = List.of("https://habrastorage.org/r/w1560/getpro/habr" +
                    "/upload_files/0b7/801/68f/0b780168f9aca7ced566032c14ebe23f.png",
            "https://ultravds.com/wp-content/uploads/2023/10/756533742439601.jpg",
            "https://blog.skillfactory.ru/wp-content/uploads/2023/02/java1-1.png",
            "https://static.tildacdn.com/tild3232-6161-4564-b832-633763393539/CHemu-dolzhny-nauchi.jpg",
            "https://248006.selcdn.ru/main/upload/setka_images" +
                    "/15014919032020_e3ea06ecc4efe66fd609360c227a5daace25eda6.png");

    private final FileHandlingHttpService fileHandlingHttpService;

    @Override
    public void attachImageToMail(MimeMessageHelper mimeMessageHelper,
                                  @Value("${default.mail.image.path}") String defaultMailImagePath) {
        List<CompletableFuture<String>> completableFutures = DEFAULT_IMAGE_LINKS.stream()
                .map(imageLink -> fileHandlingHttpService.downloadImage(imageLink, defaultMailImagePath,
                        DEFAULT_IMAGE_LINKS.indexOf(imageLink)))
                .peek(completableFuture -> {
                    String value;
                    try {
                        value = completableFuture.get();
                        mimeMessageHelper.addAttachment(value, new File(defaultMailImagePath +
                                value.substring(value.lastIndexOf('/'))));
                    } catch (InterruptedException | ExecutionException | MessagingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
    }
}

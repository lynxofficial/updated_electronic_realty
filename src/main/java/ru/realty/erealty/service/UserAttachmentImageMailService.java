package ru.realty.erealty.service;

import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.concurrent.ExecutionException;

public interface UserAttachmentImageMailService {

    void attachImageToMail(MimeMessageHelper mimeMessageHelper, String defaultMailImagePath)
            throws ExecutionException, InterruptedException;
}

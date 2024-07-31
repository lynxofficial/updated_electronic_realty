package ru.realty.erealty.dto;

import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.javamail.MimeMessageHelper;

@Builder
@Getter
@Setter
public class MimeMessageHelperDto {
    private MimeMessageHelper mimeMessageHelper;
    private MimeMessage mimeMessage;
    private String from;
    private String to;
    private String subject;
    private String content;
}


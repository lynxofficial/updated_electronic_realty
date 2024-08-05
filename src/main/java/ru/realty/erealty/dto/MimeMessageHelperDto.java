package ru.realty.erealty.dto;

import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import org.springframework.mail.javamail.MimeMessageHelper;

@Builder
public record MimeMessageHelperDto(MimeMessageHelper mimeMessageHelper,
                                   MimeMessage mimeMessage,
                                   String from,
                                   String to,
                                   String subject,
                                   String content) {
}

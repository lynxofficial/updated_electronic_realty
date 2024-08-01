package ru.realty.erealty.entity.support;

import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.Data;
import org.springframework.mail.javamail.MimeMessageHelper;

@Builder
@Data
public class MimeMessageHelperSupport {
    private MimeMessageHelper mimeMessageHelper;
    private MimeMessage mimeMessage;
    private String from;
    private String to;
    private String subject;
    private String content;
}

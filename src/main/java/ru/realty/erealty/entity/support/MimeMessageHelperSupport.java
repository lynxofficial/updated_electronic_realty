package ru.realty.erealty.entity.support;

import lombok.Builder;
import lombok.Data;
import org.springframework.mail.javamail.MimeMessageHelper;

@Builder
@Data
public class MimeMessageHelperSupport {
    private MimeMessageHelper mimeMessageHelper;
}

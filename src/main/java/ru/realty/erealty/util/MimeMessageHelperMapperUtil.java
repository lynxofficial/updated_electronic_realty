package ru.realty.erealty.util;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.realty.erealty.dto.MimeMessageHelperDto;

import java.io.UnsupportedEncodingException;

@Named("MimeMessageHelperMapperUtil")
@Component
@RequiredArgsConstructor
public class MimeMessageHelperMapperUtil {
    @Named("generateMimeMessageHelper")
    public MimeMessageHelper generateMimeMessageHelper(final MimeMessageHelperDto mimeMessageHelperDto) {
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessageHelperDto.mimeMessage(), true);
            mimeMessageHelper.setFrom(mimeMessageHelperDto.from(), "Egor");
            mimeMessageHelper.setTo(mimeMessageHelperDto.to());
            mimeMessageHelper.setSubject(mimeMessageHelperDto.subject());
            mimeMessageHelper.setText(mimeMessageHelperDto.content(), true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return mimeMessageHelper;
    }
}

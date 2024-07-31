package ru.realty.erealty.mapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.entity.support.MimeMessageHelperSupport;

import java.io.UnsupportedEncodingException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MimeMessageHelperMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "mimeMessageHelper", expression = "java(generateMimeMessageHelper("
            + "mimeMessageHelperDto.getMimeMessage(),"
            + "mimeMessageHelperDto.getFrom(),"
            + "mimeMessageHelperDto.getTo(),"
            + "mimeMessageHelperDto.getSubject(),"
            + "mimeMessageHelperDto.getContent()"
            + "))")
    MimeMessageHelperSupport fromMimeMessageHelperDto(MimeMessageHelperDto mimeMessageHelperDto);

    @Named("generateMimeMessageHelper")
    default MimeMessageHelper generateMimeMessageHelper(
            MimeMessage mimeMessage,
            String from,
            String to,
            String subject,
            String content
    ) {
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from, "Egor");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return mimeMessageHelper;
    }
}

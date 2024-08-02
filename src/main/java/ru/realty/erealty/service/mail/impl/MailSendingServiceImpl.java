package ru.realty.erealty.service.mail.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.dto.SimpleMailMessageDto;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.mapper.MimeMessageHelperMapper;
import ru.realty.erealty.mapper.SimpleMailMessageMapper;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.token.ResetTokenGenerationService;
import ru.realty.erealty.service.file.FileHandlingSystemService;
import ru.realty.erealty.service.mail.MailSendingService;
import ru.realty.erealty.util.MailDataProvider;

@Service
@RequiredArgsConstructor
public class MailSendingServiceImpl implements MailSendingService {
    private final JavaMailSender javaMailSender;
    private final FileHandlingSystemService fileHandlingSystemService;
    private final UserRepository userRepository;
    private final ResetTokenGenerationService resetTokenGenerationService;
    private final MimeMessageHelperMapper mimeMessageHelperMapper;
    private final SimpleMailMessageMapper simpleMailMessageMapper;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendEmail(
            final User user,
            final String url
    ) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelperDto mimeMessageHelperDto = MailDataProvider
                    .mimeMessageHelperDtoBuilder(email, user, url, mimeMessage)
                    .build();
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperMapper.fromMimeMessageHelperDto(mimeMessageHelperDto)
                    .getMimeMessageHelper();
            fileHandlingSystemService.runAsyncAttachImage(fileHandlingSystemService.attachImage(mimeMessageHelper));
            new Thread(() -> javaMailSender.send(mimeMessage)).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String sendEmail(final User user) {
        try {
            User currentUser = userRepository
                    .findByEmail(user.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            String resetLink = resetTokenGenerationService.generateResetToken(currentUser);
            SimpleMailMessageDto simpleMailMessageDto = MailDataProvider
                    .simpleMailMessageDtoBuilder(email, currentUser, resetLink)
                    .build();
            SimpleMailMessage simpleMailMessage = simpleMailMessageMapper
                    .fromSimpleMailMessageDto(simpleMailMessageDto);
            new Thread(() -> javaMailSender.send(simpleMailMessage)).start();
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

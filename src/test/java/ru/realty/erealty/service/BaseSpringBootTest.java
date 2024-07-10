package ru.realty.erealty.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public abstract class BaseSpringBootTest {
    @MockBean
    protected AgencyRepository agencyRepository;
    @Autowired
    protected AgencyServiceImpl agencyServiceImpl;
    @Autowired
    protected AgencyTemplateFillingServiceImpl agencyTemplateFillingServiceImpl;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected UserServiceImpl userServiceImpl;
    @Autowired
    protected CommonUserAuthorizationService commonUserAuthorizationService;
    @MockBean
    protected CustomTokenRepository customTokenRepository;
    @Autowired
    protected CustomTokenServiceImpl customTokenServiceImpl;
    @Autowired
    protected DigitalSignatureGenerationServiceImpl digitalSignatureGenerationServiceImpl;
    @MockBean
    protected UserDownloadingFileHttpResponseService userDownloadingFileHttpResponseService;
    @Autowired
    protected FileWritingService fileWritingService;
    @Autowired
    protected FileHandlingHttpResponseService fileHandlingHttpResponseService;
    @Autowired
    protected FileHandlingSystemService fileHandlingSystemService;
    @MockBean
    protected PreparingCompletableFutureAttachmentService preparingCompletableFutureAttachmentService;
    @Autowired
    protected JavaMailSender javaMailSender;
    @MockBean
    protected RealtyObjectRepository realtyObjectRepository;
    @Autowired
    protected HomeTemplateFillingServiceImpl homeTemplateFillingService;
    @Autowired
    protected ResetTokenGenerationService resetTokenGenerationService;
    @Autowired
    protected MailSendingServiceImpl mailSendingServiceImpl;
    @MockBean
    protected MimeMessageHelper mimeMessageHelper;
}

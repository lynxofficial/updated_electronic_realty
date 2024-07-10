package ru.realty.erealty.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public abstract class BaseSpringBootTest {
    @MockBean
    protected AgencyRepository agencyRepository;
    @MockBean
    protected CustomTokenRepository customTokenRepository;
    @MockBean
    protected RealtyObjectRepository realtyObjectRepository;
    @Autowired
    protected AgencyServiceImpl agencyServiceImpl;
    @Autowired
    protected AgencyTemplateFillingServiceImpl agencyTemplateFillingServiceImpl;
    @MockBean
    protected UserRepository userRepository;
    @Autowired
    protected UserServiceImpl userServiceImpl;
    @Autowired
    protected CommonUserAuthorizationService commonUserAuthorizationService;
    @Autowired
    protected CustomTokenServiceImpl customTokenServiceImpl;
    @Autowired
    protected DigitalSignatureGenerationServiceImpl digitalSignatureGenerationServiceImpl;
    @Autowired
    protected UserDownloadingFileHttpResponseService userDownloadingFileHttpResponseService;
    @Autowired
    protected FileWritingService fileWritingService;
    @Autowired
    protected FileHandlingHttpResponseService fileHandlingHttpResponseService;
    @Autowired
    protected FileHandlingSystemService fileHandlingSystemService;
    @Autowired
    protected PreparingCompletableFutureAttachmentService preparingCompletableFutureAttachmentService;
    @Autowired
    protected JavaMailSender javaMailSender;
    @Autowired
    protected HomeTemplateFillingServiceImpl homeTemplateFillingService;
    @Autowired
    protected ResetTokenGenerationService resetTokenGenerationService;
    @Autowired
    protected MailSendingServiceImpl mailSendingServiceImpl;
    @Autowired
    protected RealtyObjectServiceImpl realtyObjectServiceImpl;
    @Autowired
    protected RealtyObjectTemplateFillingServiceImpl realtyObjectTemplateFillingServiceImpl;
    @Autowired
    protected RegistrationTemplateFillingServiceImpl registrationTemplateFillingServiceImpl;
    @Autowired
    protected ResetTokenGenerationServiceImpl resetTokenGenerationServiceImpl;
    @Autowired
    protected UserTemplateFillingServiceImpl userTemplateFillingServiceImpl;
}

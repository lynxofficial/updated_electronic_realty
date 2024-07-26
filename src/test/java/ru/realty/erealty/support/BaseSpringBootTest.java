package ru.realty.erealty.support;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import ru.realty.erealty.config.CustomAuthSuccessHandler;
import ru.realty.erealty.repository.AgencyRepository;
import ru.realty.erealty.repository.CustomTokenRepository;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.agency.impl.AgencyServiceImpl;
import ru.realty.erealty.service.template.agency.impl.AgencyTemplateFillingServiceImpl;
import ru.realty.erealty.service.common.CommonUserAuthorizationService;
import ru.realty.erealty.service.custom.impl.CustomTokenServiceImpl;
import ru.realty.erealty.service.file.FileHandlingHttpResponseService;
import ru.realty.erealty.service.file.FileHandlingSystemService;
import ru.realty.erealty.service.future.PreparingCompletableFutureAttachmentService;
import ru.realty.erealty.service.mail.impl.MailSendingServiceImpl;
import ru.realty.erealty.service.realty.impl.RealtyObjectServiceImpl;
import ru.realty.erealty.service.signature.impl.DigitalSignatureGenerationServiceImpl;
import ru.realty.erealty.service.template.home.impl.HomeTemplateFillingServiceImpl;
import ru.realty.erealty.service.template.realty.impl.RealtyObjectTemplateFillingServiceImpl;
import ru.realty.erealty.service.template.registration.impl.RegistrationTemplateFillingServiceImpl;
import ru.realty.erealty.service.template.user.impl.UserTemplateFillingServiceImpl;
import ru.realty.erealty.service.token.ResetTokenGenerationService;
import ru.realty.erealty.service.token.impl.ResetTokenGenerationServiceImpl;
import ru.realty.erealty.service.user.UserDownloadingFileHttpResponseService;
import ru.realty.erealty.service.user.impl.UserServiceImpl;
import ru.realty.erealty.support.container.BaseSpringBootTestContainer;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application.yml")
@PropertySource("classpath:application-test.yml")
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = {
        BaseSpringBootTestContainer.PostgresqlContainerInitializer.class,
        BaseSpringBootTestContainer.RedisContainerInitializer.class
})
@ExtendWith(MockitoExtension.class)
public class BaseSpringBootTest {
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
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected CustomAuthSuccessHandler customAuthSuccessHandler;
    @Autowired
    protected CacheManager cacheManager;

    @BeforeAll
    public static void initPostgresqlContainer() {
        BaseSpringBootTestContainer.POSTGRESQL_CONTAINER.start();
    }

    @BeforeAll
    public static void initRedisContainer() {
        BaseSpringBootTestContainer.REDIS_CONTAINER.start();
    }

    @BeforeEach
    public void clearRedisCache() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }
}

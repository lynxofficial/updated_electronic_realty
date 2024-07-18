package ru.realty.erealty.support.container;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface BaseSpringBootTestContainer {
    @Container
    @ServiceConnection
    PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14");

    @DynamicPropertySource
    private static void configurePostgresqlContainerProperties(final DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        dynamicPropertyRegistry.add("spring.flyway.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.flyway.user", POSTGRESQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.flyway.password", POSTGRESQL_CONTAINER::getPassword);
    }

    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull final ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword(),
                    "spring.flyway.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.flyway.user=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.flyway.password=" + POSTGRESQL_CONTAINER.getPassword()
            ).applyTo(applicationContext);
        }
    }
}

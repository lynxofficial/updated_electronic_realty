package ru.realty.erealty.support.container;

import com.redis.testcontainers.RedisContainer;
import lombok.NonNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public interface BaseSpringBootTestContainer {
    @Container
    @ServiceConnection
    PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14");
    @Container
    @ServiceConnection
    RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:5"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    private static void configurePostgresqlContainerProperties(final DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        dynamicPropertyRegistry.add("spring.flyway.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.flyway.user", POSTGRESQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.flyway.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @DynamicPropertySource
    private static void configureRedisContainerProperties(final DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        dynamicPropertyRegistry.add("spring.data.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

    class RedisContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NonNull final ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                            "spring.data.redis.host=" + REDIS_CONTAINER.getHost(),
                            "spring.data.redis.port=" + REDIS_CONTAINER.getFirstMappedPort()
                    )
                    .applyTo(applicationContext);
        }
    }

    class PostgresqlContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NonNull final ConfigurableApplicationContext applicationContext) {
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

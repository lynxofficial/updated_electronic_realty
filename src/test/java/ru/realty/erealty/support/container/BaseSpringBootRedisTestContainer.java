package ru.realty.erealty.support.container;

import com.redis.testcontainers.RedisContainer;
import lombok.NonNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public interface BaseSpringBootRedisTestContainer {
    @Container
    @ServiceConnection
    RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:5"))
            .withExposedPorts(6379);

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
}

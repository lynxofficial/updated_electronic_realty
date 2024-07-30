package ru.realty.erealty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RedisCacheManagerConfig {
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(5L));
    }

    @Bean
    public RedisCacheManager redisCacheManager(final RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = getCaches().stream()
                .collect(
                        HashMap::new,
                        (configurationHashMap, key) -> configurationHashMap.put(key, redisCacheConfiguration()),
                        HashMap::putAll
                );
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration())
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .build();
    }

    private List<String> getCaches() {
        return List.of(
                "AgencyTemplateFillingServiceImplCache",
                "HomeTemplateFillingServiceImplCache",
                "RealtyObjectTemplateFillingServiceImplCache",
                "UserTemplateFillingServiceImplCache"
        );
    }
}

package inc.evil.stock.config.caching;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class CachingConfiguration {

    @ConditionalOnProperty(name = "caching.enabled", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    @EnableCaching
    @EnableConfigurationProperties(CachingProperties.class)
    static class RedisCacheConfig {
        @Bean
        @ConditionalOnProperty(name = "caching.type", havingValue = "redis", matchIfMissing = true)
        public CacheManager cacheManager(RedisConnectionFactory connectionFactory, CachingProperties cachingProperties) {
            Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
            for (Map.Entry<String, CacheConfig> cacheEntry : cachingProperties.getConfiguredCaches().entrySet()) {
                String cacheName = cacheEntry.getKey();
                CacheConfig cacheConfig = cacheEntry.getValue();
                if (cacheConfig.isEnabled()) {
                    cacheConfigurations.put(cacheName, RedisCacheConfiguration.defaultCacheConfig()
                            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                            .disableCachingNullValues()
                            .entryTtl(Duration.ofMinutes(cacheConfig.getEntryTtlMinutes())));
                }
            }
            return RedisCacheManager.builder(connectionFactory)
                    .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(cachingProperties.getDefaultEntryTtlMinutes())))
                    .withInitialCacheConfigurations(cacheConfigurations)
                    .enableStatistics()
                    .build();
        }
    }

    @ConditionalOnProperty(name = "caching.enabled", havingValue = "true")
    @Configuration(proxyBeanMethods = false)
    @EnableCaching
    @EnableConfigurationProperties(CachingProperties.class)
    static class MapCacheConfig {
        @Bean
        @ConditionalOnProperty(name = "caching.type", havingValue = "map")
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(
                    CacheNames.INVESTMENT_CACHE,
                    CacheNames.INVESTMENT_RECORDS_CACHE,
                    CacheNames.USER_CACHE,
                    CacheNames.USER_ROLES_CACHE
            );
        }
    }
}

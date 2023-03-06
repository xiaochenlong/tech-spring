package vip.corejava.app.config;

import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class AppRedisConfiguration {

    @Bean
    public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
        return configuration -> {
            configuration.useSingleServer().setConnectionPoolSize(4);
            configuration.useSingleServer().setConnectionMinimumIdleSize(1);
        };
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
        RedisSerializer redisSerializer = new JdkSerializationRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer);
        CacheKeyPrefix prefix = CacheKeyPrefix.prefixed("tech:");
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig().computePrefixWith(prefix)
                .serializeValuesWith(serializationPair);

        return (builder) -> {
            builder
                    .cacheDefaults(configuration.entryTtl(Duration.ofHours(1)))
                    .withCacheConfiguration("cert", configuration.entryTtl(Duration.ofHours(2)));
        };
    }
}

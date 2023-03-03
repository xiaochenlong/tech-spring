package vip.corejava.app.config;

import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppRedisConfiguration {

    @Bean
    public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
        return configuration -> {
            configuration.useSingleServer().setConnectionPoolSize(4);
            configuration.useSingleServer().setConnectionMinimumIdleSize(1);
        };
    }
}

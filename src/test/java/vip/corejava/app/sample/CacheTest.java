package vip.corejava.app.sample;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import vip.corejava.app.web.Response;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xcl
 * @date 2022/11/28
 */
@SpringBootTest
@Slf4j
public class CacheTest {

    @Resource
    private CacheManager cacheManager;

    @Test
    void c1() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(s -> {
            log.info("-----{}", s);
        });
        Cache def = cacheManager.getCache("default");
        def.put("xxx","xcl");
        String xxx = def.get("xxx",String.class);
        log.info("``````{}",xxx);
    }
}

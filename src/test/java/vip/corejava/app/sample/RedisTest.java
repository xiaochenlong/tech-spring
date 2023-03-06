package vip.corejava.app.sample;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import vip.corejava.app.web.Response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xcl
 * @date 2022/11/28
 */
@SpringBootTest
@Slf4j
public class RedisTest {


    @Resource
    RedissonClient redisson;

    @Test
    void t1() {

        RAtomicLong atomicLong = redisson.getAtomicLong("myAtomicLong");
        atomicLong.set(3);
        atomicLong.incrementAndGet();
        long l = atomicLong.get();
        log.info("----->{}", l);


    }

    @Test
    void t2() {

        RBucket<Response> bucket = redisson.getBucket("anyObject");
        bucket.set(Response.ok());
        Response obj = bucket.get();
        log.info("-1-{}", obj);
        boolean b = bucket.trySet(Response.ok());
        log.info("-2-{}", b);
        boolean b1 = bucket.compareAndSet(Response.ok(), Response.f());
        log.info("-3-{}", b1);
        Response andSet = bucket.getAndSet(Response.ok());
        log.info("-4-{}", andSet);

    }

    @Test
    void t3() {

        RTopic topic = redisson.getTopic("anyTopic");

        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                log.info("-1-{}-{}", channel, msg);
            }
        });

        RTopic topic2 = redisson.getTopic("anyTopic");
        long clientsReceivedMessage = topic.publish("124");
        log.info("-clientsReceivedMessage-{}", clientsReceivedMessage);
    }

    @Test
    void t4() {

        RRateLimiter rateLimiter = redisson.getRateLimiter("myRateLimiter");
        // 初始化
        // 最大流速 = 每1秒钟产生10个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);

        CountDownLatch latch = new CountDownLatch(2);
        rateLimiter.acquire(3);

        Thread t = new Thread(() -> {
            rateLimiter.acquire(2);
        });
    }

    @Test
    void t_lock() throws Exception {
        RLock lock = redisson.getLock("myLock");

        Thread thread = new Thread(() -> {
            try {
                log.info("-res2-{}", "----");
                boolean res2 = lock.tryLock(1, TimeUnit.SECONDS);
                log.info("-res2-{}", res2);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        boolean res2 = lock.tryLock(31, TimeUnit.SECONDS);
        log.info("-res4-{}", res2);
        lock.unlock();
    }
}

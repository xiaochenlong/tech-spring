package vip.corejava.app.client;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xcl
 * @date 2022/12/12
 */
@FeignClient(name = "openClient", url = "${open.url:http://localhost}", configuration = OpenClient.Configuration.class)
public interface OpenClient {

    @GetMapping("/token")
    String token(@RequestParam(value = "key") String key);

    @Slf4j
    class Configuration {

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        RequestInterceptor targetInterceptor() {
            return requestTemplate -> {
                requestTemplate.target("https://open.uat.sheca.com");
                requestTemplate.query("nonce", System.currentTimeMillis() + "" + System.nanoTime());
            };
        }


    }
}

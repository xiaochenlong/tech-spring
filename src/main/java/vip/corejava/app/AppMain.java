package vip.corejava.app;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xcl
 * @date 2022/11/28
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class AppMain {


    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }

}

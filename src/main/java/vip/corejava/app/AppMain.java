package vip.corejava.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

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

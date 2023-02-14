package vip.corejava.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author xcl
 * @date 2022/11/28
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@ServletComponentScan(value = "vip.corejava.app.web")
public class AppMain {

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }

}

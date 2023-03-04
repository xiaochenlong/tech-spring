package vip.corejava.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

/**
 * @author xcl
 * @date 2022/12/2
 */
@Configuration
public class HttpAuthenticationConfigurer extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("demo").password("111").authorities("admin");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    }
}

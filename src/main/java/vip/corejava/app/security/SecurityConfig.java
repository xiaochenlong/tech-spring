package vip.corejava.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author xcl
 * @date 2022/11/30
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public JwtSecurityContextRepository securityContextRepository() {
        return new JwtSecurityContextRepository();
    }

    @Bean
    public JsonExceptionHandler jsonExceptionHandler() {

        return new JsonExceptionHandler();
    }

    @Bean
    public HttpAuthorizationManager HttpAuthorizationManager() {
        HttpAuthorizationManager httpAuthorizationManager = new HttpAuthorizationManager();
        return httpAuthorizationManager;
    }

    /**
     * @param http
     * @return
     * @throws Exception
     * @see WebSecurityConfiguration
     * @see org.springframework.security.config.annotation.web.configuration.HttpSecurityConfiguration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.headers().disable();
        http.logout().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
        http.anonymous().disable();
        http.requestCache().disable();
        http.sessionManagement().disable();
        http.servletApi().disable();
        http.securityContext().securityContextRepository(securityContextRepository());

        http.exceptionHandling()
                .authenticationEntryPoint(jsonExceptionHandler())
                .accessDeniedHandler(jsonExceptionHandler());

        http.authorizeHttpRequests()
                .requestMatchers("/permit/**").permitAll()
                .requestMatchers("/mgmt/**").access(HttpAuthorizationManager())
                .anyRequest().authenticated();


        return http.build();
    }


    /**
     * @return
     * @see WebSecurity
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers("/resources/**");
            web.ignoring().requestMatchers("/public/**");
            web.ignoring().requestMatchers("/error");
        };
    }

}

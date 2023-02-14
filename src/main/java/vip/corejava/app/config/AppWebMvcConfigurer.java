package vip.corejava.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vip.corejava.app.json.StringTrimDeserializer;
import vip.corejava.app.web.ExceptionFilter;
import vip.corejava.app.web.TraceFilter;
import vip.corejava.app.web.TrimRequestFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xcl
 * @date 2022/12/2
 */
@Configuration
@EnableFeignClients(basePackages = {"vip.corejava.app.client"})
public class AppWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public FilterRegistrationBean exceptionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setFilter(new ExceptionFilter());
        registration.setName("exceptionFilter");
        //从小到大
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean traceFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setFilter(new TraceFilter());
        registration.setName("traceFilter");
        //从小到大
        registration.setOrder(Integer.MIN_VALUE + 1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean trimRequestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setFilter(new TrimRequestFilter());
        registration.setName("trimRequestFilter");
        //从小到大
        registration.setOrder(Integer.MIN_VALUE + 1);
        return registration;
    }



    /**
     * 覆盖.JacksonObjectMapperConfiguration
     *
     * @param builder
     * @return ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dtf));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dtf));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(df));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(df));
        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(String.class, StringTrimDeserializer.instance);
        ObjectMapper objectMapper = builder.createXmlMapper(false)
                .modules(javaTimeModule, simpleModule).build();
        SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
        //NULL处理
        objectMapper.setSerializerFactory(serializerFactory);
        return objectMapper;
    }

}

package vip.corejava.app.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AppAsyncConfigurer implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return getTaskExecutor("Executor-");
    }

    private ThreadPoolTaskExecutor getTaskExecutor(String prefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix(prefix);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }


    @Bean(name = AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(BeanFactory beanFactory) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster(beanFactory);
        multicaster.setTaskExecutor(getTaskExecutor("Event-"));
        return multicaster;
    }

}

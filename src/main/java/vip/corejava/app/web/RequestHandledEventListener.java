package vip.corejava.app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author xcl
 * @date 2022/12/13
 */
//@Component
@Slf4j
public class RequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent> {

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
       log.info("[RequestHandledEventListener]{}", event.getDescription());
    }
}

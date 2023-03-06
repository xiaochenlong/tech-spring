package vip.corejava.app.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LogAopHandler {


    @Pointcut("execution(* vip.corejava.app..*Controller.*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = point.getArgs();
        Object retVal = null;
        try {
            retVal = point.proceed(args);
            return retVal;
        } catch (Exception e) {
            throw e;
        } finally {
            StringBuffer url = null;
            String name = null;
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                HttpServletRequest request = requestAttributes.getRequest();
                url = request.getRequestURL();
            }
            long endTime = System.currentTimeMillis();
            log.info("{请求接口:[{}],userName:[{}]耗时:{}ms,参数:{},返回值:{}}", url, name, endTime - startTime, args, retVal);
        }
    }
}

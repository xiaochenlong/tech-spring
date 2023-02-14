package vip.corejava.app.web;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAopHandler {

    //自动注入request
    @Resource
    private HttpServletRequest request;


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
            long endTime = System.currentTimeMillis();
            String name = "";
            log.info("{请求接口:[{}],userName:[{}]耗时:{}ms,参数:{},返回值:{}}", request.getRequestURL(), name, endTime - startTime, args, retVal);
        }
    }
}

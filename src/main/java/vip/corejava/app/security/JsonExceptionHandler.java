package vip.corejava.app.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;


/**
 * @author xcl
 * @date 2022/12/1
 */
public class JsonExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)  {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
    }
}

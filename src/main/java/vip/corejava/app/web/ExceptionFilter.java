package vip.corejava.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author xcl
 * @date 2021/4/2
 */
@Slf4j
public class ExceptionFilter extends HttpFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("服务器异常", e);
            response.setContentType("application/json;charset=utf-8");
            Response data = Response.serverError().setMsg("服务器异常,稍后再试!");
            response.getWriter().println(objectMapper.writeValueAsString(data));
        }
    }

}

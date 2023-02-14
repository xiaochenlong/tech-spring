package vip.corejava.app.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class TraceFilter extends HttpFilter {

    private final static String traceIdHeader = "traceId";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String traceId = request.getHeader(traceIdHeader);
            if (Objects.isNull(traceId)) {
                traceId = UUID.randomUUID().toString();
            }
            MDC.put(traceIdHeader, traceId);
            super.doFilter(request, response, chain);
        } finally {
            MDC.clear();
        }
    }
}

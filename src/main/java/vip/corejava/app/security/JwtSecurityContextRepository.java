package vip.corejava.app.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author xcl
 * @date 2022/12/1
 */
@Slf4j
public class JwtSecurityContextRepository implements SecurityContextRepository {


    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        return SecurityContextRepository.super.loadDeferredContext(request);
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(getAuthentication(request));
        return securityContext;
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        List<GrantedAuthority> authorities = Collections.EMPTY_LIST;
        LoginPrincipal principal = new LoginPrincipal();
        principal.id = -1l;
        principal.admin = true;
        principal.name = "mock";
        principal.nickName = "mock";
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        return authentication;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        log.info("[saveContext]{}", context.getAuthentication());
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
    }
}

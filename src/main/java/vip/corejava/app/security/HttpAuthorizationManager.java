package vip.corejava.app.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import java.util.function.Supplier;

/**
 * @author xcl
 * @date 2022/12/1
 */
@Slf4j
public class HttpAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        boolean granted = isGranted(authentication.get(), context);
        AuthorizationDecision decision = new AuthorizationDecision(granted);
        return decision;
    }

    private boolean isGranted(Authentication authentication, RequestAuthorizationContext context) {
        return authentication != null && authentication.isAuthenticated() && isAuthorized(authentication, context);
    }

    private boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String path = UrlPathHelper.defaultInstance.getPathWithinApplication(request);
        log.info("isAuthorized:{}---{}", authentication, path);
        return true;
    }
}

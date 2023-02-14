package vip.corejava.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.Objects;


/**
 * @author xcl
 */
public class LoginPrincipalHolder {

    public static LoginPrincipal getLoginPrincipal() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.isNull(context)) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (Objects.isNull(context) || Objects.isNull(authentication) || Objects.isNull(authentication.getPrincipal())) {
            return null;
        }
        LoginPrincipal loginPrincipal = (LoginPrincipal) authentication.getPrincipal();
        return loginPrincipal;
    }

    public static LoginPrincipal getRequiredLoginPrincipal() {
        LoginPrincipal loginPrincipal = getLoginPrincipal();
        Assert.notNull(loginPrincipal, "未登录");
        return loginPrincipal;
    }
}

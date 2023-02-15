package vip.corejava.app.security;


import org.springframework.security.core.AuthenticatedPrincipal;

/**
 * 当前登录 Principal
 * @author xcl
 */
public class LoginPrincipal implements AuthenticatedPrincipal {
    public Long id;
    public String name;
    public String nickName;
    public boolean admin;
    public String key;

    @Override
    public String getName() {
        return name;
    }
}

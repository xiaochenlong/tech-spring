package vip.corejava.app.web;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * @author xcl
 * @date 2022/11/30
 */
public class TrimRequestWrapper extends HttpServletRequestWrapper {

    public TrimRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        return parameter == null ? null : parameter.trim();
    }
}

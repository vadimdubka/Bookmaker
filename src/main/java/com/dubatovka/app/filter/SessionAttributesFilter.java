package com.dubatovka.app.filter;

import com.dubatovka.app.entity.User;
import com.dubatovka.app.config.ConfigConstant;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.dubatovka.app.config.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.config.ConfigConstant.ATTR_ROLE;

/**
 * The class provides security filter for servlet container which monitors each session to have locale and role attributes set.
 */
@WebFilter(
        filterName = "SecurityFilter",
        servletNames = {ConfigConstant.FRONT_CONTROLLER},
        initParams = {@WebInitParam(name = "ATTR_ROLE", value = ATTR_ROLE),
                @WebInitParam(name = "ATTR_LOCALE", value = ATTR_LOCALE)},
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class SessionAttributesFilter implements Filter {
    
    /**
     * Role attribute name.
     */
    private String role;
    /**
     * Locale attribute name.
     */
    private String locale;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        role = filterConfig.getInitParameter("ATTR_ROLE");
        locale = filterConfig.getInitParameter("ATTR_LOCALE");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Object userRole = session.getAttribute(role);
        Object userLocale = session.getAttribute(locale);
        if (userRole == null) {
            userRole = User.UserRole.GUEST;
            session.setAttribute(role, userRole);
        }
        if (userLocale == null) {
            userLocale = ConfigConstant.LOCALE_DEFAULT;
            session.setAttribute(locale, userLocale);
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        role = null;
        locale = null;
    }
}

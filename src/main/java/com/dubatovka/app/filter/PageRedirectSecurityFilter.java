package com.dubatovka.app.filter;


import com.dubatovka.app.manager.ConfigConstant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class provides security filter for servlet container which prevents application users custom site navigation.
 */
@WebFilter(
        filterName = "PageRedirectFilter",
        urlPatterns = {"/pages/*", "/employee/*"},
        initParams = {@WebInitParam(name = "PAGE_INDEX", value = ConfigConstant.PAGE_INDEX)}
)
public class PageRedirectSecurityFilter implements Filter {
    
    /**
     * Path to index page.
     */
    private String indexPath;
    
    @Override
    public void init(FilterConfig config) throws ServletException {
        indexPath = config.getInitParameter("PAGE_INDEX");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        indexPath = null;
    }
}
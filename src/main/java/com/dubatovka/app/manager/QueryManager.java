package com.dubatovka.app.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_PREV_QUERY;
import static com.dubatovka.app.manager.ConfigConstant.PARAMETER_SEPARATOR;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PASSWORD;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PASSWORD_AGAIN;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PASSWORD_OLD;
import static com.dubatovka.app.manager.ConfigConstant.QUERY_START_SEPARATOR;
import static com.dubatovka.app.manager.ConfigConstant.VALUE_SEPARATOR;

/**
 * The class provides helper for work with queries.
 */
public final class QueryManager {
    private static final String STUB = "********";
    
    /**
     * Outer forbidding to create this class instances.
     */
    private QueryManager() {
    }
    
    /**
     * Saves query to {@link HttpSession} as {@link ConfigConstant#ATTR_PREV_QUERY} attribute.
     */
    public static void saveQueryToSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String query = buildQueryString(request);
        session.setAttribute(ATTR_PREV_QUERY, query);
    }
    
    /**
     * Builds query by parsing request parameters.
     */
    private static String buildQueryString(HttpServletRequest request) {
        String uri = request.getRequestURI();
        StringBuffer query = new StringBuffer();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String value = request.getParameter(key);
            if (key.equalsIgnoreCase(PARAM_PASSWORD) ||
                        key.equalsIgnoreCase(PARAM_PASSWORD_AGAIN) ||
                        key.equalsIgnoreCase(PARAM_PASSWORD_OLD)) {
                value = STUB;
            }
            query = query.append(PARAMETER_SEPARATOR).append(key).append(VALUE_SEPARATOR).append(value);
        }
        
        String result = uri;
        if (query.length() > 0) {
            query.deleteCharAt(0);
            result = result + QUERY_START_SEPARATOR + query;
        }
        return result;
    }
}
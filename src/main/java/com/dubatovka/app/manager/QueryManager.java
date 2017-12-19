package com.dubatovka.app.manager;

import com.dubatovka.app.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

import static com.dubatovka.app.manager.ConfigConstant.*;

/**
 * The class provides helper for work with queries.
 */
public final class QueryManager {
    
    private static final Logger logger = LogManager.getLogger(QueryManager.class);
    
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
     * Takes saved to {@link HttpSession} query.
     */
    public static String takePreviousQuery(HttpServletRequest request) {
        String prevQuery = (String) request.getSession().getAttribute(ATTR_PREV_QUERY);
        if (prevQuery == null) {
            prevQuery = PAGE_INDEX;
        }
        return prevQuery;
    }
    
    /**
     * Logs query built from given request.
     */
    public static void logQuery(HttpServletRequest request) {
        logger.log(Level.INFO, buildLog(request));
    }
    
    /**
     * Logs multipart query built from given request.
     */
    public static void logMultipartQuery(HttpServletRequest request, String query) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTR_USER);
        logger.log(Level.INFO, buildLog(query, user));
    }
    
    /**
     * Builds log due to query and user role.
     */
    public static String buildLog(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTR_USER);
        String query = buildQueryString(request);
        return buildLog(query, user);
    }
    
    /**
     * Builds log due to query and user role.
     */
    private static String buildLog(String query, User user) {
        String log;
        if (user == null) {
            log = "guest called " + query;
        } else {
            String email = user.getEmail();
            String role = user.getRole().getRole();
            int id = user.getId();
            log = role + " " + email + " (id=" + id + ") called " + query;
        }
        return log;
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
                    key.equalsIgnoreCase(PARAM_PASSWORD_OLD) ||
                    key.equalsIgnoreCase(PARAM_SECRET_ANSWER)) {
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
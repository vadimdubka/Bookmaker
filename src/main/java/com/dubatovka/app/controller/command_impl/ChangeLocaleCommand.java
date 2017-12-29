package com.dubatovka.app.controller.command_impl;


import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class ChangeLocaleCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale  = request.getParameter(PARAM_LOCALE);
        if (locale != null) {
            session.setAttribute(ATTR_LOCALE, locale);
        } else {
            session.setAttribute(ATTR_LOCALE, LOCALE_DEFAULT);
        }
        return PageNavigator.FORWARD_PREV_QUERY;
    }
}
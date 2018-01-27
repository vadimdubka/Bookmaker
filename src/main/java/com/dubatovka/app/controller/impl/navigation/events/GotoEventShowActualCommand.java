package com.dubatovka.app.controller.impl.navigation.events;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_EVENT_GOTO_TYPE;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_EVENT_QUERY_TYPE;
import static com.dubatovka.app.manager.ConfigConstant.EVENT_GOTO_SHOW_ACTUAL;
import static com.dubatovka.app.manager.ConfigConstant.EVENT_QUERY_TYPE_ACTUAL;

public class GotoEventShowActualCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_ACTUAL);
        session.setAttribute(ATTR_EVENT_GOTO_TYPE, EVENT_GOTO_SHOW_ACTUAL);
        return PageNavigator.FORWARD_GOTO_MAIN;
    }
}

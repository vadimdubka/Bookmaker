package com.dubatovka.app.controller.impl.navigation.events;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;


public class GotoEventManageCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_NOT_STARTED);
        session.setAttribute(ATTR_EVENT_GOTO_TYPE, EVENT_GOTO_MANAGE);
        return PageNavigator.FORWARD_GOTO_MAIN;
    }
}

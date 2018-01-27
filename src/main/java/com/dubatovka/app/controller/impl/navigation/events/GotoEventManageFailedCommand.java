package com.dubatovka.app.controller.impl.navigation.events;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_EVENT_GOTO_TYPE;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_EVENT_QUERY_TYPE;
import static com.dubatovka.app.manager.ConfigConstant.EVENT_GOTO_MANAGE_FAILED;
import static com.dubatovka.app.manager.ConfigConstant.EVENT_QUERY_TYPE_FAILED;

public class GotoEventManageFailedCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_FAILED);
        session.setAttribute(ATTR_EVENT_GOTO_TYPE, EVENT_GOTO_MANAGE_FAILED);
        return PageNavigator.FORWARD_GOTO_MAIN;
    }
}

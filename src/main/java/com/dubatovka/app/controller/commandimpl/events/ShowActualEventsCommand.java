package com.dubatovka.app.controller.commandimpl.events;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;

import static com.dubatovka.app.controller.commandimpl.navigation.GotoMainCommand.ATTR_EVENT_QUERY_TYPE;
import static com.dubatovka.app.manager.ConfigConstant.EVENT_QUERY_TYPE_ACTUAL;

public class ShowActualEventsCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_ACTUAL);
        return PageNavigator.FORWARD_GOTO_MAIN;
    }
}

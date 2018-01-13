package com.dubatovka.app.controller.impl.events;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class CorrectCoefficientCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_ACTUAL);
        session.setAttribute(ATTR_EVENT_COMMAND_TYPE, EVENT_COMMAND_CORRECT_COEFFICIENT);
        return PageNavigator.FORWARD_GOTO_MAIN;
    }
}

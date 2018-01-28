package com.dubatovka.app.controller.impl.navigation.events;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.config.ConfigConstant.ATTR_EVENT_GOTO_TYPE;
import static com.dubatovka.app.config.ConfigConstant.ATTR_EVENT_QUERY_TYPE;
import static com.dubatovka.app.config.ConfigConstant.EVENT_GOTO_SHOW_TO_PAY;
import static com.dubatovka.app.config.ConfigConstant.EVENT_QUERY_TYPE_TO_PAY;
/**
 * The class provides navigation to page for winning events payment.
 *
 * @author Dubatovka Vadim
 */
public class GotoEventToPayCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_TO_PAY);
        session.setAttribute(ATTR_EVENT_GOTO_TYPE, EVENT_GOTO_SHOW_TO_PAY);
        return PageNavigator.FORWARD_GOTO_MAIN;
    }
}

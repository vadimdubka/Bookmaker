package com.dubatovka.app.controller.impl.authorization;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;

/**
 * The class provides logout command implementation.
 *
 * @author Dubatovka Vadim
 */
public class LogoutCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return PageNavigator.REDIRECT_GOTO_INDEX;
    }
}

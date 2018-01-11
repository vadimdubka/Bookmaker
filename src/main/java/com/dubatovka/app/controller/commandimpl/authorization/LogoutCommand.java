package com.dubatovka.app.controller.commandimpl.authorization;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return PageNavigator.REDIRECT_GOTO_INDEX;
    }
}

package com.dubatovka.app.controller.command.impl.authorization;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return PageNavigator.REDIRECT_GOTO_INDEX;
    }
}

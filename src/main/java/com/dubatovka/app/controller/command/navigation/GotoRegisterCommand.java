package com.dubatovka.app.controller.command.navigation;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.manager.QueryManager;

import javax.servlet.http.HttpServletRequest;

public class GotoRegisterCommand implements Command {

    /**
     * Saves current query to session and navigates to register page for guest.
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_REGISTER;
    }
}
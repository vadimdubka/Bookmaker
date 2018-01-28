package com.dubatovka.app.controller.impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.service.QueryService;

import javax.servlet.http.HttpServletRequest;

public class GotoRegisterCommand implements Command {

    /**
     * Saves current query to session and navigates to register page for guest.
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        QueryService.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_REGISTER;
    }
}
package com.dubatovka.app.controller.commandimpl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.manager.QueryManager;

import javax.servlet.http.HttpServletRequest;

public class GotoIndexCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_INDEX;
    }
}

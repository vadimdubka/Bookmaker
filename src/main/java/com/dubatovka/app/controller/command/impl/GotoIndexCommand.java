package com.dubatovka.app.controller.command.impl;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.manager.QueryManager;

import javax.servlet.http.HttpServletRequest;

public class GotoIndexCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_INDEX;
    }
}

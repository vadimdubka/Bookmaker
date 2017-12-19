package com.dubatovka.app.controller.command.impl;

import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class GotoIndexCommand implements Command {
    
    public PageNavigator execute(HttpServletRequest request) {
        return PageNavigator.REDIRECT_PAGE_INDEX;
    }
}

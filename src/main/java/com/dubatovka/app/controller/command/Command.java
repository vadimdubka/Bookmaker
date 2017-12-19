package com.dubatovka.app.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    PageNavigator execute(HttpServletRequest request);
}
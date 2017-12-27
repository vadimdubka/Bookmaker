package com.dubatovka.app.controller;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    PageNavigator execute(HttpServletRequest request);
}
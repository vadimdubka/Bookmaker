package com.dubatovka.app.controller.command;

import static com.dubatovka.app.manager.ConfigConstant.*;

public enum PageNavigator {
    
    FORWARD_PAGE_MANAGE_PLAYER(PAGE_MANAGE_PLAYER, FORWARD),
    
    FORWARD_GOTO_PAGINATION(GOTO_PAGINATION, FORWARD),
    
    REDIRECT_PAGE_INDEX(PAGE_INDEX, REDIRECT),
    FORWARD_PAGE_INDEX(PAGE_INDEX, FORWARD),
    FORWARD_GOTO_INDEX(GOTO_INDEX, FORWARD),
    REDIRECT_GOTO_INDEX(GOTO_INDEX, REDIRECT),
    
    FORWARD_PAGE_REGISTER(PAGE_REGISTER, FORWARD),
    FORWARD_GOTO_REGISTER(GOTO_REGISTER, FORWARD),
    REDIRECT_GOTO_REGISTER(GOTO_REGISTER, REDIRECT),
    
    FORWARD_PAGE_MAIN(PAGE_MAIN, FORWARD),
    FORWARD_GOTO_MAIN(GOTO_MAIN, FORWARD),
    REDIRECT_GOTO_MAIN(GOTO_MAIN, REDIRECT),
    
    FORWARD_PREV_QUERY(PREV_QUERY, FORWARD),
    REDIRECT_PREV_QUERY(PREV_QUERY, REDIRECT);
    
    private final String query;
    private final String responseType;
    
    PageNavigator(String query, String responseType) {
        this.query = query;
        this.responseType = responseType;
    }
    
    public String getQuery() {
        return query;
    }
    
    public String getResponseType() {
        return responseType;
    }
}
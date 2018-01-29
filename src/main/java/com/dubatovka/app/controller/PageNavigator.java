package com.dubatovka.app.controller;

import static com.dubatovka.app.config.ConfigConstant.FORWARD;
import static com.dubatovka.app.config.ConfigConstant.GOTO_INDEX;
import static com.dubatovka.app.config.ConfigConstant.GOTO_MAIN;
import static com.dubatovka.app.config.ConfigConstant.GOTO_PAGINATION;
import static com.dubatovka.app.config.ConfigConstant.GOTO_PLAYER_STATE;
import static com.dubatovka.app.config.ConfigConstant.GOTO_REGISTER;
import static com.dubatovka.app.config.ConfigConstant.PAGE_INDEX;
import static com.dubatovka.app.config.ConfigConstant.PAGE_MAIN;
import static com.dubatovka.app.config.ConfigConstant.PAGE_MAKE_BET;
import static com.dubatovka.app.config.ConfigConstant.PAGE_MANAGE_PLAYER;
import static com.dubatovka.app.config.ConfigConstant.PAGE_PLAYER_STATE;
import static com.dubatovka.app.config.ConfigConstant.PAGE_REGISTER;
import static com.dubatovka.app.config.ConfigConstant.PREV_QUERY;
import static com.dubatovka.app.config.ConfigConstant.REDIRECT;

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
    REDIRECT_PREV_QUERY(PREV_QUERY, REDIRECT),
    FORWARD_PAGE_MAKE_BET(PAGE_MAKE_BET, FORWARD),
    FORWARD_GOTO_PLAYER_STATE(GOTO_PLAYER_STATE, FORWARD),
    FORWARD_PAGE_PLAYER_STATE(PAGE_PLAYER_STATE, FORWARD);
    
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
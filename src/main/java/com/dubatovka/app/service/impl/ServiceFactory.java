package com.dubatovka.app.service.impl;

import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.OutcomeService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.TransactionService;
import com.dubatovka.app.service.UserService;
import com.dubatovka.app.service.ValidatorService;

public final class ServiceFactory {
    
    private ServiceFactory() {
    }
    
    public static BetService getBetService() {
        return new BetServiceImpl();
    }
    
    public static CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }
    
    public static EventService getEventService() {
        return new EventServiceImpl();
    }
    
    public static OutcomeService getOutcomeService() {
        return new OutcomeServiceImpl();
    }
    
    public static PlayerService getPlayerService() {
        return new PlayerServiceImpl();
    }
    
    public static TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }
    
    public static UserService getUserService() {
        return new UserServiceImpl();
    }
    
    public static ValidatorService getValidatorService() {
        return new ValidatorServiceImpl();
    }
}
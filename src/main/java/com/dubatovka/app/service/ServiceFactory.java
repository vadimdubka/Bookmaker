package com.dubatovka.app.service;

import com.dubatovka.app.service.impl.BetServiceImpl;
import com.dubatovka.app.service.impl.CategoryServiceImpl;
import com.dubatovka.app.service.impl.EventServiceImpl;
import com.dubatovka.app.service.impl.OutcomeServiceImpl;
import com.dubatovka.app.service.impl.PlayerServiceImpl;
import com.dubatovka.app.service.impl.TransactionServiceImpl;
import com.dubatovka.app.service.impl.UserServiceImpl;
import com.dubatovka.app.service.impl.ValidatorServiceImpl;

// TODO Надо ли делать работу с сервисами через интерфейс
public final class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    
    private ServiceFactory() {
    }
    
    public static ServiceFactory getInstance() {
        return INSTANCE;
    }
    
    public BetService getBetService() {
        return new BetServiceImpl();
    }
    
    public CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }
    
    public EventService getEventService() {
        return new EventServiceImpl();
    }
    
    public OutcomeService getOutcomeService() {
        return new OutcomeServiceImpl();
    }
    
    public PlayerService getPlayerService() {
        return new PlayerServiceImpl();
    }
    
    public TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }
    
    public UserService getUserService() {
        return new UserServiceImpl();
    }
    
    public ValidatorService getValidatorService() {
        return new ValidatorServiceImpl();
    }
}
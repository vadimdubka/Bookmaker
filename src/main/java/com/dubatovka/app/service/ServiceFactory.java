package com.dubatovka.app.service;

import com.dubatovka.app.service.impl.BetServiceImpl;
import com.dubatovka.app.service.impl.CategoryServiceImpl;
import com.dubatovka.app.service.impl.EventServiceImpl;
import com.dubatovka.app.service.impl.OutcomeServiceImpl;
import com.dubatovka.app.service.impl.PlayerServiceImpl;
import com.dubatovka.app.service.impl.TransactionServiceImpl;
import com.dubatovka.app.service.impl.UserServiceImpl;
import com.dubatovka.app.service.impl.ValidatorServiceImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO Надо ли делать работу с сервисами через интерфейс
public final class ServiceFactory {
    private static final Lock LOCK = new ReentrantLock();
    private static ServiceFactory instance = null;
    
    private ServiceFactory() {
    }
    
    public static ServiceFactory getInstance() {
        LOCK.lock();
        try {
            if (instance == null) {
                instance = new ServiceFactory();
            }
        } finally {
            LOCK.unlock();
        }
        return instance;
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
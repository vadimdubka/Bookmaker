package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.OutcomeService;
import com.dubatovka.app.service.PaginationService;
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
    
    public static BetService getBetService(DAOHelper daoHelper) {
        return new BetServiceImpl(daoHelper);
    }
    
    public static CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }
    
    public static CategoryService getCategoryService(DAOHelper daoHelper) {
        return new CategoryServiceImpl(daoHelper);
    }
    
    public static EventService getEventService() {
        return new EventServiceImpl();
    }
    
    public static EventService getEventService(DAOHelper daoHelper) {
        return new EventServiceImpl(daoHelper);
    }
    
    public static OutcomeService getOutcomeService() {
        return new OutcomeServiceImpl();
    }
    
    public static OutcomeService getOutcomeService(DAOHelper daoHelper) {
        return new OutcomeServiceImpl(daoHelper);
    }
    
    public static PaginationService getPaginationService() {
        return new PaginationServiceImpl();
    }
    
    public static PlayerService getPlayerService() {
        return new PlayerServiceImpl();
    }
    
    public static PlayerService getPlayerService(DAOHelper daoHelper) {
        return new PlayerServiceImpl(daoHelper);
    }
    
    public static TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }
    
    public static TransactionService getTransactionService(DAOHelper daoHelper) {
        return new TransactionServiceImpl(daoHelper);
    }
    
    public static UserService getUserService() {
        return new UserServiceImpl();
    }
    
    public static UserService getUserService(DAOHelper daoHelper) {
        return new UserServiceImpl(daoHelper);
    }
    
    public static ValidatorService getValidatorService() {
        return new ValidatorServiceImpl();
    }
}
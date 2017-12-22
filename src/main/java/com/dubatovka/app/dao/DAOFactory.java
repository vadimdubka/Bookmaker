package com.dubatovka.app.dao;

import com.dubatovka.app.dao.impl.BetDAOImpl;
import com.dubatovka.app.dao.impl.CategoryDAOImpl;
import com.dubatovka.app.dao.impl.DocumentDAOImpl;
import com.dubatovka.app.dao.impl.EventDAOImpl;
import com.dubatovka.app.dao.impl.OutcomeDAOImpl;
import com.dubatovka.app.dao.impl.PlayerDAOImpl;
import com.dubatovka.app.dao.impl.StandardDAOImpl;
import com.dubatovka.app.dao.impl.TransactionDAOImpl;
import com.dubatovka.app.dao.impl.UserDAOImpl;

public final class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();
    private BetDAO betDAO;
    private CategoryDAO categoryDAO;
    private DocumentDAO documentDAO;
    private EventDAO eventDAO;
    private OutcomeDAO outcomeDAO;
    private PlayerDAO playerDAO;
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;
    private StandardDAO standardDAO; //TODO yдалить по окончанию проекта
    
    private DAOFactory() {
    }
    
    public static DAOFactory getInstance() {
        return INSTANCE;
    }
    
    public BetDAO getBetDAO() {
        if (betDAO == null) {
            betDAO = new BetDAOImpl();
        }
        return betDAO;
    }
    
    public CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAOImpl();
        }
        return categoryDAO;
    }
    
    public DocumentDAO getDocumentDAO() {
        if (documentDAO == null) {
            documentDAO = new DocumentDAOImpl();
        }
        return documentDAO;
    }
    
    public EventDAO getEventDAO() {
        if (eventDAO == null) {
            eventDAO = new EventDAOImpl();
        }
        return eventDAO;
    }
    
    public OutcomeDAO getOutcomeDAO() {
        if (outcomeDAO == null) {
            outcomeDAO = new OutcomeDAOImpl();
        }
        return outcomeDAO;
    }
    
    public PlayerDAO getPlayerDAO() {
        if (playerDAO == null) {
            playerDAO = new PlayerDAOImpl();
        }
        return playerDAO;
    }
    
    public TransactionDAO getTransactionDAO() {
        if (transactionDAO == null) {
            transactionDAO = new TransactionDAOImpl();
        }
        return transactionDAO;
    }
    
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
    
    public StandardDAO getStandardDAO() {
        if (standardDAO == null) {
            standardDAO = new StandardDAOImpl();
        }
        return standardDAO;
    }
    
}
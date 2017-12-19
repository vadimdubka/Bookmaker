package com.dubatovka.app.dao;

import com.dubatovka.app.dao.impl.*;

public final class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();
    private PlayerDAO playerDAO;
    private UserDAO userDAO;
    private DocumentDAO documentDAO;
    
    private DAOFactory() {
    }
    
    public static DAOFactory getInstance() {
        return INSTANCE;
    }
    
    public DocumentDAO getDocumentDAO() {
        if (documentDAO == null) {
            documentDAO = new DocumentDAOImpl();
        }
        return documentDAO;
    }
    
    public PlayerDAO getPlayerDAO() {
        if (playerDAO == null) {
            playerDAO = new PlayerDAOImpl();
        }
        return playerDAO;
    }
    
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
}
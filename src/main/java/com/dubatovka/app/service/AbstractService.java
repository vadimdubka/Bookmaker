package com.dubatovka.app.service;

import com.dubatovka.app.dao.DAOFactory;

abstract class AbstractService {
    
    DAOFactory daoFactory;
    
    AbstractService() {
        this.daoFactory = DAOFactory.getInstance();
    }
    
}
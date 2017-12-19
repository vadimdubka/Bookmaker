package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.DAOFactory;

abstract class AbstractService {
    
    DAOFactory daoFactory;
    
    AbstractService() {
        this.daoFactory = DAOFactory.getInstance();
    }
    
}
package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;

import java.util.Set;

public abstract class BetService extends AbstractService {
    public abstract Set<Object> mockMethod(String id);
    
    protected BetService() {
    }
    
    protected BetService(DAOHelper daoHelper) {
        super(daoHelper);
    }
}

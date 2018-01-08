package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;

import java.util.Set;

public abstract class OutcomeService extends AbstractService {
    protected OutcomeService() {
    }
    
    protected OutcomeService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract  Set<Object> mockMethod(String id);
}

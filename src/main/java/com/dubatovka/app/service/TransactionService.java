package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;

import java.util.Set;

public abstract class TransactionService extends AbstractService {
    protected TransactionService() {
    }
    
    protected TransactionService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract Set<Object> mockMethod(String id);
    
}

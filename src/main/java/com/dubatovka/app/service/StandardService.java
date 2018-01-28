package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;

import java.util.Set;

//TODO удалить по завершению проекта
public abstract class StandardService extends AbstractService {
    protected StandardService() {
    }
    
    protected StandardService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    public abstract  Set<Object> mockMethod(String id);
}

package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;

import java.util.Set;

//TODO удалить по завершению проекта
public abstract class StandardService extends AbstractService {
    protected StandardService() {
    }
    
    protected StandardService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract  Set<Object> mockMethod(String id);
}

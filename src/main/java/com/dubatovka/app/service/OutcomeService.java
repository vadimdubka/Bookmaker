package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.MessageManager;

import java.util.Set;

public abstract class OutcomeService extends AbstractService {
    protected OutcomeService() {
    }
    
    protected OutcomeService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract void setOutcomesForEvent(Event event);
    
    public abstract void insertOutcomeSet(Set<Outcome> outcomeSet, MessageManager messageManager, StringBuilder errorMessage);
}

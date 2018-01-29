package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;

import java.util.Set;

public abstract class OutcomeService extends DAOProviderHolder {
    protected OutcomeService() {
    }
    
    protected OutcomeService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    public abstract void setOutcomesForEvent(Event event);
    
    public abstract void insertOutcomeSet(Set<Outcome> outcomeSet, MessageService messageService);
}

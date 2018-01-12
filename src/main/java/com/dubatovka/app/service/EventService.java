package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Event;

import java.util.Map;
import java.util.Set;

public abstract class EventService extends AbstractService {
    
    protected EventService() {
    }
    
    protected EventService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract Event getEvent(int eventId);
    
    public abstract Set<Event> getEvents(String categoryId, String eventQueryType);
    
    public abstract Map<Integer, Integer> countEvents(String eventQueryType);
    
    public abstract Map<String, Map<String, String>> getOutcomeColumnMaps(Set<Event> eventSet);
}

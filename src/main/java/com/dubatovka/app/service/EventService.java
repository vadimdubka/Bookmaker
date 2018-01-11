package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Event;

import java.util.Map;
import java.util.Set;

public abstract class EventService extends AbstractService{
    
    protected EventService() {
    }
    
    protected EventService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract Set<Event> getActualEventsByCategoryId(String categoryId);
    
    public abstract  Set<Event> getAllEventsByCategoryId(String categoryId);
    
    public abstract Map<String, Map<String, String>> getOutcomeColumnMaps(Set<Event> eventSet);
    
    public abstract  Event getEventById(int eventId);
    
    public abstract Map<Integer,Integer> countActualEventsGroupByCategory();
}

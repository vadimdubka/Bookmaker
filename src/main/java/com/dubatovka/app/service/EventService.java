package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.MessageManager;

import java.util.List;
import java.util.Map;

public abstract class EventService extends AbstractService {
    
    protected EventService() {
    }
    
    protected EventService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract Event getEvent(int eventId);
    
    public abstract Event getEvent(String eventIdStr);
    
    public abstract List<Event> getEvents(String categoryId, String eventQueryType);
    
    public abstract Map<Integer, Integer> countEvents(String eventQueryType);
    
    public abstract Map<String, Map<String, String>> getOutcomeColumnMaps(List<Event> events);
    
    public abstract void deleteEvent(int eventId, MessageManager messageManager, StringBuilder errorMessage);
    
    public abstract void insertEvent(Event event, MessageManager messageManager, StringBuilder errorMessage);
    
    public abstract void updateEventInfo(Event event, MessageManager messageManager, StringBuilder errorMessage);
    
    public abstract void updateEventResult(Event event, MessageManager messageManager, StringBuilder errorMessage);
}

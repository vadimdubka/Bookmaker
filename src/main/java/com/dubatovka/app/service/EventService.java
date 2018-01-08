package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;

import java.util.Map;
import java.util.Set;

public abstract class EventService extends AbstractService{
    public abstract Set<Event> getActualEventsByCategoryId(String categoryId);
    
    public abstract  Set<Event> getAllEventsByCategoryId(String categoryId);
    
    public abstract Map<String, Map<String, String>> getOutcomeColumnMaps(Set<Event> eventSet);
    
    public abstract  Event getEventById(String eventId);
}

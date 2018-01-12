package com.dubatovka.app.service;

import com.dubatovka.app.dao.exception.DAOException;
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
    
    public abstract Event getEventById(int eventId);
    
    public abstract Set<Event> getNewEventsByCategoryId(String categoryId);
    
    public abstract Set<Event> getActualEventsByCategoryId(String categoryId);
    
    public abstract Set<Event> getNotStartedEventsByCategoryId(String categoryId);
    
    public abstract Set<Event> getStartedEventsByCategoryId(String categoryId);
    
    public abstract Set<Event> getFailedEventsByCategoryId(String categoryId);
    
    public abstract Set<Event> getClosedEventsByCategoryId(String categoryId);
    
    public abstract Map<Integer, Integer> countNewEventsByCategories() throws DAOException;
    
    public abstract Map<Integer, Integer> countActualEventsGroupByCategory();
    
    public abstract Map<Integer, Integer> countNotStartedEventsByCategories() throws DAOException;
    
    public abstract Map<Integer, Integer> countStartedEventsByCategories() throws DAOException;
    
    public abstract Map<Integer, Integer> countFailedEventsByCategories() throws DAOException;
    
    public abstract Map<Integer, Integer> countClosedEventsByCategories() throws DAOException;
    
    public abstract Map<String, Map<String, String>> getOutcomeColumnMaps(Set<Event> eventSet);
}

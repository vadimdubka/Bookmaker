package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.EventDAO;
import com.dubatovka.app.dao.OutcomeDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.service.EventService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class EventServiceImpl extends EventService {
    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    
    private final EventDAO eventDAO = daoHelper.getEventDAO();
    private final OutcomeDAO outcomeDAO = daoHelper.getOutcomeDAO();
    
    private final Map<String, Map<String, String>> coeffColumnMaps = new HashMap<>();
    private final Map<String, String> type1 = new HashMap<>();
    private final Map<String, String> typeX = new HashMap<>();
    private final Map<String, String> type2 = new HashMap<>();
    
    {
        coeffColumnMaps.put(OUTCOME_TYPE_1, type1);
        coeffColumnMaps.put(OUTCOME_TYPE_X, typeX);
        coeffColumnMaps.put(OUTCOME_TYPE_2, type2);
        
        type1.put(OUTCOME_TYPE_NAME_KEY, OUTCOME_TYPE_1);
        typeX.put(OUTCOME_TYPE_NAME_KEY, OUTCOME_TYPE_X);
        type2.put(OUTCOME_TYPE_NAME_KEY, OUTCOME_TYPE_2);
    }
    
    EventServiceImpl() {
        
    }
    
    EventServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public Event getEventById(int eventId) {
        Event event = null;
        try {
            event = eventDAO.getEventById(eventId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        setOutcomesForEvent(event);
        return event;
    }
    
    @Override
    public Map<Integer, Integer> countActualEventsGroupByCategory() {
        Map<Integer, Integer> eventCountMap = null;
        try {
            eventCountMap = eventDAO.countActualEventsGroupByCategory();
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return eventCountMap;
    }
    
    @Override
    public Set<Event> getAllEventsByCategoryId(String categoryId) {
        Set<Event> eventSet = null;
        try {
            eventSet = eventDAO.getAllEventsByCategoryId(categoryId);
            setOutcomesForEvents(eventSet);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return eventSet;
    }
    
    @Override
    public Set<Event> getActualEventsByCategoryId(String categoryId) {
        Set<Event> eventSet = null;
        try {
            eventSet = eventDAO.getActualEventsByCategoryId(categoryId);
            setOutcomesForEvents(eventSet);
//            removeEventsWithoutOutcomes(eventSet);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return eventSet;
    }
    
    @Override
    public Map<String, Map<String, String>> getOutcomeColumnMaps(Set<Event> eventSet) {
        eventSet.forEach(event -> {
            int id = event.getId();
            fillOutcomeColumnMaps(id, event.getOutcomeSet());
        });
        
        return coeffColumnMaps;
    }
    
    private void setOutcomesForEvents(Iterable<Event> eventSet) {
        eventSet.forEach(this::setOutcomesForEvent);
    }
    
    private void setOutcomesForEvent(Event event) {
        int id = event.getId();
        try {
            Set<Outcome> outcomeSet = outcomeDAO.getOutcomesByEventId(id);
            event.setOutcomeSet(outcomeSet);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
    
    private void removeEventsWithoutOutcomes(Iterable<Event> eventSet) {
        Iterator<Event> eventIterator = eventSet.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            if (event.getOutcomeSet().isEmpty()) {
                eventIterator.remove();
            }
        }
    }
    
    private void fillOutcomeColumnMaps(int id, Iterable<Outcome> outcomeSet) {
        for (Outcome outcome : outcomeSet) {
            String type = outcome.getType();
            Map<String, String> typeMap = coeffColumnMaps.get(type);
            if (typeMap != null) {
                typeMap.put(String.valueOf(id), String.valueOf(outcome.getCoefficient()));
            }
        }
    }
}

package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.EventDAO;
import com.dubatovka.app.dao.OutcomeDAO;
import com.dubatovka.app.dao.exception.DAOException;
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

public class EventServiceImpl extends AbstractService implements EventService {
    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    
    private final EventDAO eventDAO = daoFactory.getEventDAO();
    private final OutcomeDAO outcomeDAO = daoFactory.getOutcomeDAO();
    
    private final Map<String, Map<String, String>> coeffColumnMaps = new HashMap<>();
    private final Map<String, String> type1 = new HashMap<>();
    private final Map<String, String> typeX = new HashMap<>();
    private final Map<String, String> type2 = new HashMap<>();
    
    public EventServiceImpl() {
        coeffColumnMaps.put(OUTCOME_TYPE_1, type1);
        coeffColumnMaps.put(OUTCOME_TYPE_X, typeX);
        coeffColumnMaps.put(OUTCOME_TYPE_2, type2);
        
        type1.put(OUTCOME_TYPE_NAME_KEY, OUTCOME_TYPE_1);
        typeX.put(OUTCOME_TYPE_NAME_KEY, OUTCOME_TYPE_X);
        type2.put(OUTCOME_TYPE_NAME_KEY, OUTCOME_TYPE_2);
    }
    
    @Override
    public Set<Event> getAllEventsByCategoryId(String categoryId) {
        Set<Event> eventSet = null;
        try {
            eventSet = eventDAO.getAllEventsByCategoryId(categoryId);
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
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return eventSet;
    }
    
    
    @Override
    public Map<String, Map<String, String>> getCoeffColumnMaps(Set<Event> eventSet) {
        Iterator<Event> eventIterator = eventSet.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            int id = event.getId();
            try {
                Set<Outcome> outcomeSet = outcomeDAO.getOutcomesByEventId(id);
                if (outcomeSet.isEmpty()) {
                    eventIterator.remove();
                } else {
                    fillCoeffColumnMaps(event.getId(), outcomeSet);
                }
            } catch (DAOException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        return coeffColumnMaps;
    }
    
    private void fillCoeffColumnMaps(int id, Iterable<Outcome> outcomeSet) {
        if (outcomeSet != null) {
            for (Outcome outcome : outcomeSet) {
                String type = outcome.getType();
                Map<String, String> typeMap = coeffColumnMaps.get(type);
                if (typeMap != null) {
                    typeMap.put(String.valueOf(id), String.valueOf(outcome.getCoefficient()));
                }
            }
        }
    }
}

package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.EventDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_SQL_OPERATION;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_SQL_TRANSACTION;
import static com.dubatovka.app.manager.ConfigConstant.OUTCOME_TYPE_KEY_NAME;

class EventServiceImpl extends EventService {
    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    
    private final EventDAO eventDAO = daoHelper.getEventDAO();
    private final BetDAO betDAO = daoHelper.getBetDAO();
    
    private final Map<String, Map<String, String>> coeffColumnMaps = new HashMap<>();
    private final Map<String, String> type1 = new HashMap<>();
    private final Map<String, String> typeX = new HashMap<>();
    private final Map<String, String> type2 = new HashMap<>();
    
    {
        coeffColumnMaps.put(Outcome.Type.TYPE_1.getType(), type1);
        coeffColumnMaps.put(Outcome.Type.TYPE_X.getType(), typeX);
        coeffColumnMaps.put(Outcome.Type.TYPE_2.getType(), type2);
        
        type1.put(OUTCOME_TYPE_KEY_NAME, Outcome.Type.TYPE_1.getType());
        typeX.put(OUTCOME_TYPE_KEY_NAME, Outcome.Type.TYPE_X.getType());
        type2.put(OUTCOME_TYPE_KEY_NAME, Outcome.Type.TYPE_2.getType());
    }
    
    EventServiceImpl() {
        
    }
    
    EventServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public Event getEvent(int eventId) {
        Event event = null;
        try {
            event = eventDAO.readEvent(eventId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        ServiceFactory.getOutcomeService(daoHelper).setOutcomesForEvent(event);
        return event;
    }
    
    @Override
    public Event getEvent(String eventIdStr) {
        Event event = null;
        try {
            int eventId = Integer.parseInt(eventIdStr);
            event = getEvent(eventId);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, String.format("%s %s %s", e.getClass(), e.getMessage(), eventIdStr));
        }
        return event;
    }
    
    @Override
    public List<Event> getEvents(String categoryId, String eventQueryType) {
        List<Event> events = null;
        try {
            if (eventQueryType != null) {
                events = eventDAO.readEvents(categoryId, eventQueryType);
                setOutcomesForEvents(events);
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return events;
    }
    
    @Override
    public Map<Integer, Integer> countEvents(String eventQueryType) {
        Map<Integer, Integer> eventCountMap = null;
        try {
            if (eventQueryType != null) {
                eventCountMap = eventDAO.countEvents(eventQueryType);
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return eventCountMap;
    }
    
    @Override
    public Map<String, Map<String, String>> getOutcomeColumnMaps(List<Event>  events) {
        if (events != null) {
            events.forEach(event -> {
                int evetId = event.getId();
                fillOutcomeColumnMaps(evetId, event.getOutcomeSet());
            });
        }
        return coeffColumnMaps;
    }
    
    @Override
    public void deleteEvent(int eventId, MessageManager messageManager, StringBuilder errorMessage) {
        try {
            eventDAO.deleteEvent(eventId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append(messageManager.getMessage(MESSAGE_ERR_SQL_OPERATION));
        }
    }
    
    @Override
    public void insertEvent(Event event, MessageManager messageManager, StringBuilder errorMessage) {
        try {
            eventDAO.insertEvent(event);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append(messageManager.getMessage(MESSAGE_ERR_SQL_OPERATION));
        }
    }
    
    @Override
    public void updateEventInfo(Event event, MessageManager messageManager, StringBuilder errorMessage) {
        try {
            eventDAO.updateEventInfo(event);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append(messageManager.getMessage(MESSAGE_ERR_SQL_OPERATION));
        }
    }
    
    @Override
    public void updateEventResult(Event event, MessageManager messageManager, StringBuilder errorMessage) {
        int eventId = event.getId();
        int result1 = Integer.parseInt(event.getResult1());
        int result2 = Integer.parseInt(event.getResult2());
        Map<Outcome.Type, Bet.Status> betStatusMap = getBetStatusMapForEventResult(result1, result2);
        try {
            daoHelper.beginTransaction();
            boolean isUpdEvent = eventDAO.updateEventResult(event);
            for (Map.Entry<Outcome.Type, Bet.Status> entrySet : betStatusMap.entrySet()) {
                betDAO.updateBetStatus(eventId, entrySet.getKey(), entrySet.getValue());
            }
            if (isUpdEvent) {
                daoHelper.commit();
            } else {
                daoHelper.rollback();
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append(messageManager.getMessage(MESSAGE_ERR_SQL_OPERATION));
        } catch (SQLException e) {
            logger.log(Level.ERROR, MESSAGE_ERR_SQL_TRANSACTION + e);
            errorMessage.append(messageManager.getMessage(MESSAGE_ERR_SQL_TRANSACTION));
        }
    }
    
    private Map<Outcome.Type, Bet.Status> getBetStatusMapForEventResult(int result1, int result2) {
        Map<Outcome.Type, Bet.Status> betStatusMap = new EnumMap<>(Outcome.Type.class);
        betStatusMap.put(Outcome.Type.TYPE_1, Bet.Status.LOSING);
        betStatusMap.put(Outcome.Type.TYPE_X, Bet.Status.LOSING);
        betStatusMap.put(Outcome.Type.TYPE_2, Bet.Status.LOSING);
        if (result1 > result2) {
            betStatusMap.put(Outcome.Type.TYPE_1, Bet.Status.WIN);
        } else if (result1 < result2) {
            betStatusMap.put(Outcome.Type.TYPE_2, Bet.Status.WIN);
        } else {
            betStatusMap.put(Outcome.Type.TYPE_X, Bet.Status.WIN);
        }
        return betStatusMap;
    }
    
    private void setOutcomesForEvents(Iterable<Event> eventSet) {
        eventSet.forEach(ServiceFactory.getOutcomeService(daoHelper)::setOutcomesForEvent);
    }
    
    private void fillOutcomeColumnMaps(int eventId, Iterable<Outcome> outcomeSet) {
        for (Outcome outcome : outcomeSet) {
            String type = outcome.getType().getType();
            Map<String, String> typeMap = coeffColumnMaps.get(type);
            if (typeMap != null) {
                typeMap.put(String.valueOf(eventId), String.valueOf(outcome.getCoefficient()));
            }
        }
    }
}

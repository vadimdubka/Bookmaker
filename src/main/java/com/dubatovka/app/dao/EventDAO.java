package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Event;

import java.util.Map;
import java.util.Set;

public interface EventDAO {
    /**
     * Column names of database table 'event'.
     */
    String ID = "id";
    String CATEGORY_ID = "category_id";
    String DATE = "date";
    String PARTICIPANT1 = "participant1";
    String PARTICIPANT2 = "participant2";
    String RESULT1 = "result1";
    String RESULT2 = "result2";
    String COUNT = "count";
    
    Event getEvent(int eventId) throws DAOException;
    
    Set<Event> readEvents(String categoryId, String eventQueryType) throws DAOException;
    
    Map<Integer, Integer> countEvents(String eventQueryType) throws DAOException;
    
    void deleteEvent(int eventId) throws DAOException;
    
    void insertEvent(Event event) throws DAOException;
    
    void updateEventInfo(Event event) throws DAOException;
    
    boolean updateEventResult(Event event) throws DAOException;
}

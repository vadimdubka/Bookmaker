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
    
    /**
     * Event query types
     */
    String NEW = "new";
    String ACTUAL = "actual";
    String NOT_STARTED = "not_started";
    String STARTED = "started";
    String FAILED = "failed";
    String CLOSED = "closed";
    
    Event getEvent(int eventId) throws DAOException;
    
    Set<Event> readEvents(String categoryId, String eventQueryType) throws DAOException;
    
    Map<Integer, Integer> countEvents(String eventQueryType) throws DAOException;
}

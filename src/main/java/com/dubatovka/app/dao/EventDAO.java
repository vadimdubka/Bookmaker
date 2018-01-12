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
    
    Event getEventById(int eventId) throws DAOException;
    
    Set<Event> readNewEventsByCategoryId(String categoryId) throws DAOException;
    
    Set<Event> readActualEventsByCategoryId(String categoryId) throws DAOException;
    
    Set<Event> readNotStartedEventsByCategoryId(String categoryId) throws DAOException;
    
    Set<Event> readStartedEventsByCategoryId(String categoryId) throws DAOException;
    
    Set<Event> readFailedEventsByCategoryId(String categoryId) throws DAOException;
    
    Set<Event> readClosedEventsByCategoryId(String categoryId) throws DAOException;
    
    Map<Integer, Integer> countNewEventsByCategories() throws DAOException;
    
    Map<Integer, Integer> countActualEventsByCategories() throws DAOException;
    
    Map<Integer, Integer> countNotStartedEventsByCategories() throws DAOException;
    
    Map<Integer, Integer> countStartedEventsByCategories() throws DAOException;
    
    Map<Integer, Integer> countFailedEventsByCategories() throws DAOException;
    
    Map<Integer, Integer> countClosedEventsByCategories() throws DAOException;
}

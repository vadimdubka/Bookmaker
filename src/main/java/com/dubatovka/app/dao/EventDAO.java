package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Event;

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
    
    Set<Event> getAllEventsByCategoryId(String categoryId) throws DAOException;
    
    Set<Event> getActualEventsByCategoryId(String categoryId) throws DAOException;
}

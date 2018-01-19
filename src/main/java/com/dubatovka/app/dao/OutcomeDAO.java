package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Outcome;

import java.util.Set;

public interface OutcomeDAO {
    /**
     * Column names of database table 'outcome'.
     */
    String EVENT_ID = "event_id";
    String TYPE = "type";
    String COEFFICIENT = "coefficient";
    
    Set<Outcome> getOutcomesByEventId(int id) throws DAOException;
    
    void insertOutcome(Outcome outcome) throws DAOException;
}

package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Bet;

import java.math.BigDecimal;
import java.util.List;

public interface BetDAO {
    /**
     * Column names of database table 'bet'.
     */
    String PLAYER_ID = "player_id";
    String EVENT_ID = "event_id";
    String OUTCOME_TYPE = "type";
    String DATE = "date";
    String COEFFICIENT = "coefficient";
    String AMOUNT = "amount";
    String STATUS = "status";
    
    void insertBet(int playerId, int eventId, String outcomeType, BigDecimal coefficient, BigDecimal betAmount) throws DAOException;
    
    List<Bet> readBetListForPlayer(int playerId) throws DAOException;
}

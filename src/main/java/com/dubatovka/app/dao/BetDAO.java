package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Outcome;

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
    
    boolean insertBet(Bet bet) throws DAOException;
    
    List<Bet> readBetListForPlayer(int playerId) throws DAOException;
    
    void updateBetStatus(int eventId, Outcome.Type type, Bet.Status status) throws DAOException;
}

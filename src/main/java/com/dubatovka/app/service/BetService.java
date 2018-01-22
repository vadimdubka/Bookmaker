package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Bet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BetService extends AbstractService {
    
    protected BetService() {
    }
    
    protected BetService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract List<Bet> getBetListForPlayer(int playerId);
    
    public abstract Set<Bet> getBetSetForEventAndStatus(int eventId, Bet.Status status);
    
    public abstract Map<String, Map<String, String>> getWinBetInfo(int categoryId);
    
    public abstract void payWinBet(int eventId, StringBuilder errorMessage);
    
    public abstract void makeBet(Bet bet, StringBuilder errorMessage);
}

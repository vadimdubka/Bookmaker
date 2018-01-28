package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.entity.Bet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BetService extends AbstractService {
    
    protected BetService() {
    }
    
    protected BetService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    public abstract List<Bet> getBetListForPlayer(int playerId);
    
    public abstract List<Bet> getBetListForPlayer(int playerId, int limit, int offset);
    
    public abstract Set<Bet> getBetSetForEventAndStatus(int eventId, Bet.Status status);
    
    public abstract Map<String, Map<String, String>> getWinBetInfo(int categoryId);
    
    public abstract void payWinBet(int eventId, MessageService messageService);
    
    public abstract void makeBet(Bet bet, MessageService messageService);
    
    public abstract int countBetsForPlayer(int playerId);
}

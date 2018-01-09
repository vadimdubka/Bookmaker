package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Bet;

import java.util.List;

public abstract class BetService extends AbstractService {
    
    protected BetService() {
    }
    
    protected BetService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract List<Bet> getBetList(int playerId);
}

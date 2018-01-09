package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.service.BetService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BetServiceImpl extends BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);
    private final BetDAO betDAO = daoHelper.getBetDAO();
    
    BetServiceImpl() {
    }
    
    BetServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public  List<Bet> getBetList(int playerId) {
        List<Bet> betList = null;
        try {
            betList = betDAO.readBetListForPlayer(playerId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return betList;
    }
    
}

package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.service.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class BetServiceImpl extends BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);
    private final BetDAO standardDAO = daoHelper.getBetDAO();
    
    BetServiceImpl() {
    }
    
    BetServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

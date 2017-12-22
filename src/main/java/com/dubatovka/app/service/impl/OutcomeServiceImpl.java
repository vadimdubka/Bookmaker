package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.OutcomeDAO;
import com.dubatovka.app.service.OutcomeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class OutcomeServiceImpl extends AbstractService implements OutcomeService {
    private static final Logger logger = LogManager.getLogger(OutcomeServiceImpl.class);
    private final OutcomeDAO standardDAO = daoFactory.getOutcomeDAO();
    
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

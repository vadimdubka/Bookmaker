package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.OutcomeDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.service.OutcomeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_SQL_OPERATION;

class OutcomeServiceImpl extends OutcomeService {
    private static final Logger logger = LogManager.getLogger(OutcomeServiceImpl.class);
    private final OutcomeDAO outcomeDAO = daoHelper.getOutcomeDAO();
    
    OutcomeServiceImpl() {
    }
    
    OutcomeServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public void setOutcomesForEvent(Event event) {
        if (event != null) {
            try {
                int id = event.getId();
                Set<Outcome> outcomeSet = outcomeDAO.getOutcomesByEventId(id);
                event.setOutcomeSet(outcomeSet);
            } catch (DAOException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
    }
    
    @Override
    public void insertOutcomeSet(Set<Outcome> outcomeSet, StringBuilder errorMessage) {
        try {
            for (Outcome outcome : outcomeSet) {
                boolean failure = !outcomeDAO.insertOutcome(outcome);
                if (failure) {
                    errorMessage.append(MESSAGE_ERR_SQL_OPERATION);
                }
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append(MESSAGE_ERR_SQL_OPERATION);
        }
    }
}

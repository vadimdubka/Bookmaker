package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.TransactionDAO;
import com.dubatovka.app.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class TransactionServiceImpl extends AbstractService implements TransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);
    private final TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

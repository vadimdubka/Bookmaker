package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.StandardDAO;
import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.service.StandardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

//TODO удалить по завершению проекта
class StandardServiceImpl extends StandardService {
    private static final Logger logger = LogManager.getLogger(StandardServiceImpl.class);
    private final StandardDAO standardDAO = daoProvider.getStandardDAO();
    
    StandardServiceImpl() {
    }
    
    StandardServiceImpl(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

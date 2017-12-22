package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.StandardDAO;
import com.dubatovka.app.service.StandardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

//TODO удалить по завершению проекта
public class StandardServiceImpl extends AbstractService implements StandardService {
    private static final Logger logger = LogManager.getLogger(StandardServiceImpl.class);
    private final StandardDAO standardDAO = daoFactory.getStandardDAO();
    
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

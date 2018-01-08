package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.StandardDAO;
import com.dubatovka.app.service.StandardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

//TODO удалить по завершению проекта
public class StandardServiceImpl extends StandardService {
    private static final Logger logger = LogManager.getLogger(StandardServiceImpl.class);
    private final StandardDAO standardDAO = daoHelper.getStandardDAO();
    
    StandardServiceImpl() {
    }
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

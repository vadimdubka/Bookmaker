package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.StandardDAO;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.service.StandardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

//TODO удалить по завершению проекта
class StandardServiceImpl extends StandardService {
    private static final Logger logger = LogManager.getLogger(StandardServiceImpl.class);
    private final StandardDAO standardDAO = daoHelper.getStandardDAO();
    
    StandardServiceImpl() {
    }
    
    StandardServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public Set<Object> mockMethod(String id) {
        return null;
    }
}

package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.db.ConnectionPool;

abstract class AbstractService implements AutoCloseable {
    
    /**
     * DAOHelper instance for this class instance use.
     */
    protected DAOHelper daoHelper;
    
    protected AbstractService() {
        daoHelper = new DAOHelper();
    }
    
    /**
     * Constructs instance using definite {@link DAOHelper} object.
     */
    protected AbstractService(DAOHelper daoHelper) {
        this.daoHelper = daoHelper;
    }
    
    /**
     * Returns {@link DAOHelper#connection} to {@link ConnectionPool}.
     */
    @Override
    public void close() {
        daoHelper.close();
    }
    
}
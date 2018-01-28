package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.dao.db.ConnectionPool;

abstract class AbstractService implements AutoCloseable {
    
    /**
     * DAOProvider instance for this class instance use.
     */
    protected DAOProvider daoProvider;
    
    protected AbstractService() {
        daoProvider = new DAOProvider();
    }
    
    /**
     * Constructs instance using definite {@link DAOProvider} object.
     */
    protected AbstractService(DAOProvider daoProvider) {
        this.daoProvider = daoProvider;
    }
    
    /**
     * Returns {@link DAOProvider#connection} to {@link ConnectionPool}.
     */
    @Override
    public void close() {
        daoProvider.close();
    }
}
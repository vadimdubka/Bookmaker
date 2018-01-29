package com.dubatovka.app.service;

import com.dubatovka.app.dao.db.ConnectionPool;
import com.dubatovka.app.dao.impl.DAOProvider;

abstract class DAOProviderHolder implements AutoCloseable {
    
    /**
     * DAOProvider instance for this class instance use.
     */
    protected DAOProvider daoProvider;
    
    protected DAOProviderHolder() {
        daoProvider = new DAOProvider();
    }
    
    /**
     * Constructs instance using definite {@link DAOProvider} object.
     */
    protected DAOProviderHolder(DAOProvider daoProvider) {
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
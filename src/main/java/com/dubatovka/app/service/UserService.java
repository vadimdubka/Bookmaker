package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.entity.User;

public abstract class UserService extends AbstractService{
    /**
     * Default instance constructor.
     */
    protected UserService() {
    }
    
    /**
     * Constructs instance using definite {@link DAOProvider} object.
     */
    protected UserService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    public abstract User authorizeUser(String email, String password);
}

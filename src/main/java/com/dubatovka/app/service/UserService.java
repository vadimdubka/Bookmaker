package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.User;

public abstract class UserService extends AbstractService{
    public abstract User authorizeUser(String email, String password);
    
    /**
     * Default instance constructor.
     */
    protected UserService() {
    }
    
    /**
     * Constructs instance using definite {@link DAOHelper} object.
     */
    protected UserService(DAOHelper daoHelper) {
        super(daoHelper);
    }
}

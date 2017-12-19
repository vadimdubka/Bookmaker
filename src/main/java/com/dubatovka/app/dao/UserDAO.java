package com.dubatovka.app.dao;


import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.User;


public abstract class UserDAO extends AbstractDBDAO {
    
    /**
     * Column names of database table 'user'.
     */
    protected static final String ID = "id";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String ROLE = "role";
    protected static final String REGISTRATION_DATE = "registration_date";
    
    
    protected UserDAO() {
    }
    
    public abstract User authorizeUser(String email, String password) throws DAOException;
    
}
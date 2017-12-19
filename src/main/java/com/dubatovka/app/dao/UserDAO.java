package com.dubatovka.app.dao;


import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.User;


public interface UserDAO {
    
    /**
     * Column names of database table 'user'.
     */
    String ID = "id";
    String EMAIL = "email";
    String PASSWORD = "password";
    String ROLE = "role";
    String REGISTRATION_DATE = "registration_date";
    
    public abstract User authorizeUser(String email, String password) throws DAOException;
    
}
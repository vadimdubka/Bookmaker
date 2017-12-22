package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;

import java.util.Set;

public interface EventDAO {
    /**
     * Column names of database table 'x'.
     */
    String ID = "id";
    
    Set<Object> mockMethod() throws DAOException;
    
}

package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;

import java.util.Set;

//TODO удалить по завершению проекта
public interface StandardDAO {
    /**
     * Column names of database table 'x'.
     */
    String ID = "id";
    
    Set<Object> mockMethod() throws DAOException;
    
}

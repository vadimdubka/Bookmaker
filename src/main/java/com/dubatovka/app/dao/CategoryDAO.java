package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Category;

import java.util.Set;

public interface CategoryDAO {
    /**
     * Column names of database table 'user'.
     */
    String ID = "id";
    String NAME = "name";
    String PARENT_ID = "parent_id";
    
    Set<Category> readAllCategories() throws DAOException;
    
    Category readCategoryById(int id) throws DAOException;
}

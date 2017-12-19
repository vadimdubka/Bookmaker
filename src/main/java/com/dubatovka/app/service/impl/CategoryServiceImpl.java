package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.service.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CategoryServiceImpl extends AbstractService implements CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private final CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
    
    private static List<Category> sportCategories;
    private static boolean isSportCategoriesModified = false;
    
    @Override
    public List<Category> getSportCategories() {
        if ((sportCategories == null) || isSportCategoriesModified) {
            try {
                sportCategories = categoryDAO.getSportCategories();
                isSportCategoriesModified = false;
            } catch (DAOException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        
        return sportCategories;
    }
}
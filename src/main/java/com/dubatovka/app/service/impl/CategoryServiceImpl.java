package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CategoryServiceImpl extends AbstractService implements CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private final CategoryDAO userDAO = daoFactory.getCategoryDAO();
    
    @Override
    public List<Category> getSportCategories() {
        return null;
    }
    
    
    
}
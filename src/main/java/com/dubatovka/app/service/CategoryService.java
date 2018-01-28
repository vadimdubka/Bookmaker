package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.entity.Category;

import java.util.Set;

public abstract class CategoryService extends AbstractService {
    
    protected CategoryService() {
    }
    
    protected CategoryService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    /**
     * Get list of sport categories.
     */
    public abstract Set<Category> getSportCategories();
    
    
    public abstract Category getCategoryById(int id);
}

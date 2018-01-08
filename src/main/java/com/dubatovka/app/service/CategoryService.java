package com.dubatovka.app.service;

import com.dubatovka.app.entity.Category;

import java.util.Set;

public abstract class CategoryService extends AbstractService {
    
    /**
     * Get list of sport categories.
     */
    public abstract Set<Category> getSportCategories();
    
    
    public abstract Category getCategoryById(int id);
}

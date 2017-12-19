package com.dubatovka.app.service;

import com.dubatovka.app.entity.Category;

import java.util.List;

public interface CategoryService {
    
    /**
     * Get list of sport categories.
     */
    public List<Category> getSportCategories();
    
}

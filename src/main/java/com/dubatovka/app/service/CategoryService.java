package com.dubatovka.app.service;

import com.dubatovka.app.entity.Category;

import java.util.Set;

public interface CategoryService {
    
    /**
     * Get list of sport categories.
     */
    Set<Category> getSportCategories();
    
}

package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.service.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CategoryServiceImpl extends AbstractService implements CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private static Set<Category> sportCategories;
    private static boolean isCategoriesModified = false; //TODO менять значение переменной при любой модификации списка категорий
    private final CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
    
    @Override
    public Set<Category> getSportCategories() {
        if ((sportCategories == null) || isCategoriesModified) {
            try {
                Set<Category> categorySet = categoryDAO.getAllCategories();
                isCategoriesModified = false;
                sportCategories = buildCategoryHierarchy(categorySet);
            } catch (DAOException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        return sportCategories;
    }
    
    private Set<Category> buildCategoryHierarchy(Collection<Category> categorySet) {
        Map<Integer, Category> sportCategoriesMap = pickOutSportCategories(categorySet);
        fillSportWithCategories (sportCategoriesMap, categorySet);
        
        Set<Category> sportCategoriesSet = new HashSet<>(sportCategoriesMap.values());
        return sportCategoriesSet;
    }
    
    private Map<Integer, Category> pickOutSportCategories(Collection<Category> categorySet) {
        Map<Integer, Category> sportCategoriesMap = new HashMap<>();
        Iterator<Category> iterator = categorySet.iterator();
        while (iterator.hasNext()) {
            Category category = iterator.next();
            if (category.getParentId() == 0) {
                category.setChildCategorySet(new HashSet<>());
                sportCategoriesMap.put(category.getId(), category);
                iterator.remove();
            }
        }
        
        return sportCategoriesMap;
    }
    
    private void fillSportWithCategories(Map<Integer, Category> sportCategoriesMap, Collection<Category> categorySet) {
        categorySet.forEach(category -> {
            int parentId = category.getParentId();
            Category sport = sportCategoriesMap.get(parentId);
            sport.getChildCategorySet().add(category);
        });
    }
}
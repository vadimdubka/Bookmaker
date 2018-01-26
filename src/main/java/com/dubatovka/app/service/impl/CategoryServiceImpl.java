package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.service.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CategoryServiceImpl extends CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private static final Lock lock = new ReentrantLock();
    private static final AtomicBoolean isCategoriesModified = new AtomicBoolean(false);
    private static Set<Category> sportCategories;
    private final CategoryDAO categoryDAO = daoHelper.getCategoryDAO();
    
    CategoryServiceImpl() {
    }
    
    CategoryServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public Set<Category> getSportCategories() {
        if ((sportCategories == null) || isCategoriesModified.get()) {
            lock.lock();
            try {
                if ((sportCategories == null) || isCategoriesModified.get()) {
                    Set<Category> categorySet = categoryDAO.readAllCategories();
                    sportCategories = buildCategoryHierarchy(categorySet);
                    isCategoriesModified.set(false);
                }
            } catch (DAOException e) {
                logger.log(Level.ERROR, e.getMessage());
            } finally {
                lock.unlock();
            }
        }
        return sportCategories;
    }
    
    @Override
    public Category getCategoryById(int id) {
        Category categoryResult = null;
        try {
            categoryResult = categoryDAO.readCategoryById(id);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        
        return categoryResult;
    }
    
    private Set<Category> buildCategoryHierarchy(Iterable<Category> categorySet) {
        Map<Integer, Category> sportCategoriesMap = pickOutSportCategories(categorySet);
        fillSportWithCategories(sportCategoriesMap, categorySet);
        
        Set<Category> sportCategoriesSet = new HashSet<>(sportCategoriesMap.values());
        return sportCategoriesSet;
    }
    
    private Map<Integer, Category> pickOutSportCategories(Iterable<Category> categorySet) {
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
    
    private void fillSportWithCategories(Map<Integer, Category> sportCategoriesMap, Iterable<Category> categorySet) {
        categorySet.forEach(category -> {
            int parentId = category.getParentId();
            Category sport = sportCategoriesMap.get(parentId);
            sport.getChildCategorySet().add(category);
        });
    }
}
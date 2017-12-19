package com.dubatovka.app.service;

import com.dubatovka.app.service.impl.CategoryServiceImpl;

// TODO Надо ли делать работу с сервисами через интерфейс
public final class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    
    private ServiceFactory() {
    }
    
    public static ServiceFactory getInstance() {
        return INSTANCE;
    }
    
    public CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }
    
}
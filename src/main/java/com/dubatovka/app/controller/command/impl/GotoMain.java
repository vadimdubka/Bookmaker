package com.dubatovka.app.controller.command.impl;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_SPORT_SET;

public class GotoMain implements Command {
    
    private static final String PARAM_CATEGORY_ID = "category_id";
    private static final String ATTR_EVENT_SET = "event_set";
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        ServiceFactory serviceFactoryInstance = ServiceFactory.getInstance();
        CategoryService categoryService = serviceFactoryInstance.getCategoryService();
        EventService eventService = serviceFactoryInstance.getEventService();
        
        Set<Category> sportList = categoryService.getSportCategories();
        request.setAttribute(ATTR_SPORT_SET, sportList);
        
        
        String categoryId = request.getParameter(PARAM_CATEGORY_ID);
        Set<Event> eventSet = eventService.getActualEventsByCategoryId(categoryId);
        request.setAttribute(ATTR_EVENT_SET, eventSet);
        
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_MAIN;
    }
}

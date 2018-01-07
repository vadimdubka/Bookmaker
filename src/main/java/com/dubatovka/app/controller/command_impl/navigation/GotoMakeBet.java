package com.dubatovka.app.controller.command_impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

public class GotoMakeBet implements Command {
    public static final String PARAM_EVENT_ID = "event_id";
    public static final String PARAM_OUTCOME_TYPE = "outcome_type";
    
    public static final String ATTR_CATEGORY = "category";
    public static final String ATTR_SPORT_CATEGORY = "sportCategory";
    public static final String ATTR_EVENT = "event";
    public static final String ATTR_OUTCOME = "outcome";
    
    private static final ServiceFactory serviceFactoryInstance = ServiceFactory.getInstance();
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        CategoryService categoryService = serviceFactoryInstance.getCategoryService();
        EventService eventService = serviceFactoryInstance.getEventService();
    
        String eventId = request.getParameter(PARAM_EVENT_ID);
        Event event = eventService.getEventById(eventId);
        String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
        Outcome outcome = event.getOutcomeByType(outcomeType);
        Category category = categoryService.getCategoryById(event.getCategoryId());
        Category parentCategory = categoryService.getCategoryById(category.getParentId());
        
        request.setAttribute(ATTR_CATEGORY, category);
        request.setAttribute(ATTR_SPORT_CATEGORY, parentCategory);
        request.setAttribute(ATTR_EVENT, event);
        request.setAttribute(ATTR_OUTCOME, outcome);
        
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_MAKE_BET;
    }
}
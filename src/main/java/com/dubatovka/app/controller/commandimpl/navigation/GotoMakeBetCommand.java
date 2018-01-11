package com.dubatovka.app.controller.commandimpl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

public class GotoMakeBetCommand implements Command {
    public static final String PARAM_EVENT_ID = "event_id";
    public static final String PARAM_OUTCOME_TYPE = "outcome_type";
    
    public static final String ATTR_CATEGORY = "category";
    public static final String ATTR_SPORT_CATEGORY = "sportCategory";
    public static final String ATTR_EVENT = "event";
    public static final String ATTR_OUTCOME = "outcome";
    
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        Event event;
        Outcome outcome;
        Category category;
        Category parentCategory;
        try (EventService eventService = ServiceFactory.getEventService(); CategoryService categoryService = ServiceFactory.getCategoryService()) {
            String eventId = request.getParameter(PARAM_EVENT_ID);
            event = eventService.getEventById(Integer.parseInt(eventId));
            String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
            outcome = event.getOutcomeByType(outcomeType);
            category = categoryService.getCategoryById(event.getCategoryId());
            parentCategory = categoryService.getCategoryById(category.getParentId());
        }
        
        request.setAttribute(ATTR_CATEGORY, category);
        request.setAttribute(ATTR_SPORT_CATEGORY, parentCategory);
        request.setAttribute(ATTR_EVENT, event);
        request.setAttribute(ATTR_OUTCOME, outcome);
        
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_MAKE_BET;
    }
}
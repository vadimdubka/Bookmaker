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
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoMain implements Command {
    public static final String PARAM_CATEGORY_ID = "category_id";
    public static final String ATTR_EVENT_SET = "event_set";
    public static final String ATTR_TYPE_1_MAP = "type_1_map";
    public static final String ATTR_TYPE_X_MAP = "type_x_map";
    public static final String ATTR_TYPE_2_MAP = "type_2_map";
    
    private static final ServiceFactory serviceFactoryInstance = ServiceFactory.getInstance();
    private final CategoryService categoryService = serviceFactoryInstance.getCategoryService();
    private final EventService eventService = serviceFactoryInstance.getEventService();
    
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        Set<Category> sportSet = categoryService.getSportCategories();
        request.setAttribute(ATTR_SPORT_SET, sportSet);
        
        String categoryId = request.getParameter(PARAM_CATEGORY_ID);
        extractActualEvents(request, categoryId);
        
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_MAIN;
    }
    
    private void extractActualEvents(HttpServletRequest request, String categoryId) {
        Set<Event> eventSet = null;
        if (categoryId != null) {
            eventSet = eventService.getActualEventsByCategoryId(categoryId);
            extractOutcomesForEvents(request, eventSet);
        }
        request.setAttribute(ATTR_EVENT_SET, eventSet);
    }
    
    private void extractOutcomesForEvents(HttpServletRequest request, Set<Event> eventSet) {
        Map<String, Map<String, String>> coeffColumnMaps = eventService.getOutcomeColumnMaps(eventSet);
        Map<String, String> type1Map = coeffColumnMaps.get(OUTCOME_TYPE_1);
        Map<String, String> typeXMap = coeffColumnMaps.get(OUTCOME_TYPE_X);
        Map<String, String> type2Map = coeffColumnMaps.get(OUTCOME_TYPE_2);
        
        request.setAttribute(ATTR_TYPE_1_MAP, type1Map);
        request.setAttribute(ATTR_TYPE_X_MAP, typeXMap);
        request.setAttribute(ATTR_TYPE_2_MAP, type2Map);
    }
}

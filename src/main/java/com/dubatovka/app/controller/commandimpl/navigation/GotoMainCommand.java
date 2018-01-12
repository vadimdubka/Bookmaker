package com.dubatovka.app.controller.commandimpl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoMainCommand implements Command {
    //TODO вынести все psf поля из команд
    public static final String PARAM_CATEGORY_ID = "category_id";
    public static final String PARAM_EVENT_QUERY_TYPE = "event_query_type";
    public static final String ATTR_EVENT_SET = "event_set";
    public static final String ATTR_TYPE_1_MAP = "type_1_map";
    public static final String ATTR_TYPE_X_MAP = "type_x_map";
    public static final String ATTR_TYPE_2_MAP = "type_2_map";
    public static final String ATTR_EVENT_COUNT_MAP = "event_count_map";
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        String categoryId = request.getParameter(PARAM_CATEGORY_ID);
        String eventQueryType =  request.getParameter(PARAM_EVENT_QUERY_TYPE);
        
        try (EventService eventService = ServiceFactory.getEventService(); CategoryService categoryService = ServiceFactory.getCategoryService()) {
            Set<Category> sportSet = categoryService.getSportCategories();
            Map<Integer, Integer> eventCountMap = eventService.countEvents(eventQueryType);
            request.setAttribute(ATTR_SPORT_SET, sportSet);
            request.setAttribute(ATTR_EVENT_COUNT_MAP, eventCountMap);
            
            if (categoryId != null) {
                Set<Event> eventSet = eventService.getEvents(categoryId, eventQueryType);
                Map<String, Map<String, String>> coeffColumnMaps = eventService.getOutcomeColumnMaps(eventSet);
                Map<String, String> type1Map = coeffColumnMaps.get(OUTCOME_TYPE_1);
                Map<String, String> typeXMap = coeffColumnMaps.get(OUTCOME_TYPE_X);
                Map<String, String> type2Map = coeffColumnMaps.get(OUTCOME_TYPE_2);
                
                request.setAttribute(ATTR_EVENT_SET, eventSet);
                request.setAttribute(ATTR_TYPE_1_MAP, type1Map);
                request.setAttribute(ATTR_TYPE_X_MAP, typeXMap);
                request.setAttribute(ATTR_TYPE_2_MAP, type2Map);
            }
        }
        
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_MAIN;
    }
}

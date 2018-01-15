package com.dubatovka.app.controller.impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoMainCommand implements Command {
    
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        String categoryId = request.getParameter(PARAM_CATEGORY_ID);
        
        HttpSession session = request.getSession();
        String eventQueryType = (String) session.getAttribute(ATTR_EVENT_QUERY_TYPE);
        String eventCommandType = (String) session.getAttribute(ATTR_EVENT_GOTO_TYPE);
        
        if ((eventQueryType == null) || (eventCommandType == null)) {
            eventQueryType = EVENT_QUERY_TYPE_ACTUAL;
            session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_ACTUAL);
            session.setAttribute(ATTR_EVENT_GOTO_TYPE, EVENT_GOTO_SHOW_ACTUAL);
        }
        
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
                request.setAttribute(ATTR_CATEGORY_ID, categoryId);
                request.setAttribute(ATTR_TYPE_1_MAP, type1Map);
                request.setAttribute(ATTR_TYPE_X_MAP, typeXMap);
                request.setAttribute(ATTR_TYPE_2_MAP, type2Map);
            }
        }
        
        QueryManager.saveQueryToSession(request);
        return PageNavigator.FORWARD_PAGE_MAIN;
    }
}

package com.dubatovka.app.controller.impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoMainCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PAGE_MAIN;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String categoryIdStr = request.getParameter(PARAM_CATEGORY_ID);
        String eventQueryType = (String) session.getAttribute(ATTR_EVENT_QUERY_TYPE);
        String eventCommandType = (String) session.getAttribute(ATTR_EVENT_GOTO_TYPE);
        
        if ((eventQueryType == null) || (eventCommandType == null)) {
            eventQueryType = EVENT_QUERY_TYPE_ACTUAL;
            session.setAttribute(ATTR_EVENT_QUERY_TYPE, EVENT_QUERY_TYPE_ACTUAL);
            session.setAttribute(ATTR_EVENT_GOTO_TYPE, EVENT_GOTO_SHOW_ACTUAL);
        }
        
        try (EventService eventService = ServiceFactory.getEventService(); CategoryService categoryService = ServiceFactory.getCategoryService(); BetService betService = ServiceFactory.getBetService()) {
            Set<Category> sportSet = categoryService.getSportCategories();
            Map<Integer, Integer> eventCountMap = eventService.countEvents(eventQueryType);
            request.setAttribute(ATTR_SPORT_SET, sportSet);
            request.setAttribute(ATTR_EVENT_COUNT_MAP, eventCountMap);
            
            if (categoryIdStr != null) {
                validateCommand(errorMessage, categoryIdStr);
                if (errorMessage.toString().trim().isEmpty()) {
                    Set<Event> eventSet = eventService.getEvents(categoryIdStr, eventQueryType);
                    Map<String, Map<String, String>> coeffColumnMaps = eventService.getOutcomeColumnMaps(eventSet);
                    Map<String, String> type1Map = coeffColumnMaps.get(Outcome.Type.TYPE_1.getType());
                    Map<String, String> typeXMap = coeffColumnMaps.get(Outcome.Type.TYPE_X.getType());
                    Map<String, String> type2Map = coeffColumnMaps.get(Outcome.Type.TYPE_2.getType());
                    request.setAttribute(ATTR_EVENT_SET, eventSet);
                    request.setAttribute(ATTR_CATEGORY_ID, categoryIdStr);
                    request.setAttribute(ATTR_TYPE_1_MAP, type1Map);
                    request.setAttribute(ATTR_TYPE_X_MAP, typeXMap);
                    request.setAttribute(ATTR_TYPE_2_MAP, type2Map);
                    
                    if (EVENT_GOTO_SHOW_TO_PAY.equals(eventCommandType)) {
                        int categoryId = Integer.parseInt(categoryIdStr);
                        Map<String, Map<String, String>> winBetInfoMap = betService.getWinBetInfo(categoryId);
                        Map<String, String> winBetCount = winBetInfoMap.get(WIN_BET_INFO_KEY_COUNT);
                        Map<String, String> winBetSum = winBetInfoMap.get(WIN_BET_INFO_KEY_SUM);
                        request.setAttribute(ATTR_WIN_BET_COUNT, winBetCount);
                        request.setAttribute(ATTR_WIN_BET_SUM, winBetSum);
                    }
                }
            }
        }
        
        QueryManager.saveQueryToSession(request);
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(StringBuilder errorMessage, String categoryIdStr) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(categoryIdStr)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

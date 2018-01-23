package com.dubatovka.app.controller.impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoMakeBetCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_GOTO_MAIN;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
        Event event = new Event();
        
        validateRequestParams(errorMessage, eventIdStr, outcomeType);
        setAndCheckEventNotNull(eventIdStr, event, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            try (CategoryService categoryService = ServiceFactory.getCategoryService()) {
                Outcome outcome = event.getOutcomeByType(outcomeType);
                Category category = categoryService.getCategoryById(event.getCategoryId());
                Category parentCategory = categoryService.getCategoryById(category.getParentId());
                request.setAttribute(ATTR_CATEGORY, category);
                request.setAttribute(ATTR_SPORT_CATEGORY, parentCategory);
                request.setAttribute(ATTR_EVENT, event);
                request.setAttribute(ATTR_OUTCOME, outcome);
                navigator = PageNavigator.FORWARD_PAGE_MAKE_BET;
            }
        }
        
        QueryManager.saveQueryToSession(request);
        setErrorMessagesToRequest(errorMessage, request);
        return navigator;
    }
}
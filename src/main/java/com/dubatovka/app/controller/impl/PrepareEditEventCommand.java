package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.dubatovka.app.controller.PageNavigator.FORWARD_GOTO_MAIN;
import static com.dubatovka.app.manager.ConfigConstant.*;

public class PrepareEditEventCommand implements Command {
    
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String editType = request.getParameter(PARAM_EVENT_EDIT_TYPE);
        validateRequestParams(errorMessage, eventIdStr);
        if (errorMessage.toString().trim().isEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService()) {
                Event event = eventService.getEvent(eventIdStr);
                Map<String, Outcome> outcomeMap = event.getOutcomeMap();
                request.setAttribute(ATTR_EVENT, event);
                request.setAttribute(ATTR_OUTCOME_MAP, outcomeMap);
                request.setAttribute(ATTR_EDIT_TYPE, editType);
                navigator = PageNavigator.FORWARD_GOTO_EDIT_EVENT;
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_INVALID_REQUEST_PARAMETER);
            navigator = FORWARD_GOTO_MAIN;
        }
        
        return navigator;
    }
    
}

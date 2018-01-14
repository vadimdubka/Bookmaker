package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.controller.PageNavigator.FORWARD_GOTO_MAIN;
import static com.dubatovka.app.manager.ConfigConstant.*;

public class EditEventCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String editType = request.getParameter(PARAM_EVENT_EDIT_TYPE);
        validateRequestParams(errorMessage, eventIdStr, editType);
        if (errorMessage.toString().trim().isEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService()) {
                Event event = eventService.getEvent(eventIdStr);
                request.setAttribute(ATTR_EVENT, event);
                request.setAttribute(ATTR_EDIT_TYPE, editType);
                
                if (editType.equals("event_delete")) {
                    deleteEvent(event);
                    navigator = PageNavigator.FORWARD_PREV_QUERY;
                } else {
                    navigator = PageNavigator.FORWARD_GOTO_EDIT_EVENT;
                }
                /*switch (editType) {
                    case "event_create":
                        navigator = PageNavigator.FORWARD_GOTO_EDIT_EVENT;
                        break;
                    case "event_change":
                        navigator = PageNavigator.FORWARD_GOTO_EDIT_EVENT;
                        break;
                    case "event_delete":
                        deleteEvent(event);
                        navigator = PageNavigator.FORWARD_PREV_QUERY;
                        break;
                    case "result_set":
                        navigator = PageNavigator.FORWARD_GOTO_SET_RESULT;
                        break;
                    case "coefficient_change":
                        navigator = PageNavigator.FORWARD_GOTO_EDIT_COEFFICIENT;
                        break;
                    default:
                        navigator = FORWARD_GOTO_MAIN;*/
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_INVALID_REQUEST_PARAMETER);
            navigator = FORWARD_GOTO_MAIN;
        }
        
        return navigator;
    }
    
    private void deleteEvent(Event event) {
    }
    
}

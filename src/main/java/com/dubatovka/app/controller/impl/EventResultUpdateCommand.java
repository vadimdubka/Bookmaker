package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class EventResultUpdateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String result1Str = request.getParameter(PARAM_RESULT_1);
        String result2Str = request.getParameter(PARAM_RESULT_2);
        
        validateRequestParams(errorMessage, result1Str, result2Str);
        if (errorMessage.toString().trim().isEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService()) {
                Event event = eventService.getEvent(eventIdStr);
                validateEventNotNull(event, errorMessage);
                validateEventResultUpdateCommand(errorMessage, result1Str, result2Str);
                if (errorMessage.toString().trim().isEmpty()) {
                    event.setResult1(result1Str.trim());
                    event.setResult2(result2Str.trim());
                    eventService.updateEventResult(event, errorMessage);
                    if (errorMessage.toString().trim().isEmpty()) {
                        request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_EVENT_UPDATE_INFO_SUCCESS);
                    } else {
                        request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_EVENT_UPDATE_INFO_FAIL);
                    }
                } else {
                    request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage.toString());
                }
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_INVALID_REQUEST_PARAMETER);
        }
    
        return PageNavigator.FORWARD_PREV_QUERY;
    }
    
    private void validateEventResultUpdateCommand(StringBuilder errorMessage, String result1Str, String result2Str) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (errorMessage.toString().trim().isEmpty()) {
            if (!validatorService.isValidEventResult(result1Str)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_RESULT).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidEventResult(result2Str)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_RESULT).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

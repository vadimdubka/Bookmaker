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
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String result1Str = request.getParameter(PARAM_RESULT_1);
        String result2Str = request.getParameter(PARAM_RESULT_2);
        Event event = new Event();
        
        validateRequestParams(errorMessage, result1Str, result2Str);
        setAndCheckEventNotNull(eventIdStr, event, errorMessage);
        validateCommand(errorMessage, result1Str, result2Str);
        if (errorMessage.toString().trim().isEmpty()) {
            event.setResult1(result1Str.trim());
            event.setResult2(result2Str.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.updateEventResult(event, errorMessage);
            }
            if (errorMessage.toString().trim().isEmpty()) {
                request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_EVENT_UPDATE_INFO_SUCCESS);
            } else {
                request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_EVENT_UPDATE_INFO_FAIL);
            }
        }
    
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(StringBuilder errorMessage, String result1Str, String result2Str) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidEventResult(result1Str)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_RESULT).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidEventResult(result2Str)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_RESULT).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

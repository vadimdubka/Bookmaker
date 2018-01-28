package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.MessageService;
import com.dubatovka.app.service.ValidationService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_EVENT_UPDATE_INFO;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_RESULT;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_INF_EVENT_UPDATE_INFO;
import static com.dubatovka.app.config.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.config.ConfigConstant.PARAM_RESULT_1;
import static com.dubatovka.app.config.ConfigConstant.PARAM_RESULT_2;

public class EventResultUpdateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        MessageService messageService = ServiceFactory.getMessageService(session);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String result1Str = request.getParameter(PARAM_RESULT_1);
        String result2Str = request.getParameter(PARAM_RESULT_2);
        Event event = new Event();
        
        validateRequestParams(messageService, result1Str, result2Str);
        setAndCheckEventNotNull(eventIdStr, event, messageService);
        validateCommand(messageService, result1Str, result2Str);
        if (messageService.isErrMessEmpty()) {
            event.setResult1(result1Str.trim());
            event.setResult2(result2Str.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.updateEventResult(event, messageService);
            }
            if (messageService.isErrMessEmpty()) {
                messageService.appendInfMessByKey(MESSAGE_INF_EVENT_UPDATE_INFO);
            } else {
                messageService.appendErrMessByKey(MESSAGE_ERR_EVENT_UPDATE_INFO);
            }
        }
        setMessagesToRequest(messageService, request);
        return navigator;
    }
    
    private void validateCommand(MessageService messageService, String result1Str, String result2Str) {
        if (messageService.isErrMessEmpty()) {
            ValidationService validationService = ServiceFactory.getValidationService();
            if (!validationService.isValidEventResult(result1Str) || !validationService.isValidEventResult(result2Str)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_RESULT);
            }
        }
    }
}

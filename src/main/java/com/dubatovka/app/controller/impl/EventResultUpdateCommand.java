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

import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_EVENT_UPDATE_INFO;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_RESULT;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_EVENT_UPDATE_INFO;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_RESULT_1;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_RESULT_2;

public class EventResultUpdateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String result1Str = request.getParameter(PARAM_RESULT_1);
        String result2Str = request.getParameter(PARAM_RESULT_2);
        Event event = new Event();
        
        validateRequestParams(messageManager, result1Str, result2Str);
        setAndCheckEventNotNull(eventIdStr, event, messageManager);
        validateCommand(messageManager, result1Str, result2Str);
        if (messageManager.isErrMessEmpty()) {
            event.setResult1(result1Str.trim());
            event.setResult2(result2Str.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.updateEventResult(event, messageManager);
            }
            if (messageManager.isErrMessEmpty()) {
                messageManager.appendInfMessByKey(MESSAGE_INF_EVENT_UPDATE_INFO);
            } else {
                messageManager.appendErrMessByKey(MESSAGE_ERR_EVENT_UPDATE_INFO);
            }
        }
        setMessagesToRequest(messageManager, request);
        return navigator;
    }
    
    private void validateCommand(MessageManager messageManager, String result1Str, String result2Str) {
        if (messageManager.isErrMessEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidEventResult(result1Str) || !validatorService.isValidEventResult(result2Str)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_RESULT);
            }
        }
    }
}

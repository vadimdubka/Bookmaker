package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_EVENT_DELETE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_EVENT_DELETE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_SEPARATOR;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EVENT_ID;

public class EventDeleteCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        
        validateRequestParams(messageManager, errorMessage, eventIdStr);
        validateCommand(messageManager, errorMessage, eventIdStr);
        if (errorMessage.toString().trim().isEmpty()) {
            int eventId = Integer.parseInt(eventIdStr);
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.deleteEvent(eventId, messageManager, errorMessage);
            }
            if (errorMessage.toString().trim().isEmpty()) {
                infoMessage.append(messageManager.getMessageByKey(MESSAGE_INF_EVENT_DELETE)).append(MESSAGE_SEPARATOR);
            } else {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_EVENT_DELETE)).append(MESSAGE_SEPARATOR);
            }
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(MessageManager messageManager, StringBuilder errorMessage, String eventIdStr) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(eventIdStr)) {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_EVENT_ID)).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

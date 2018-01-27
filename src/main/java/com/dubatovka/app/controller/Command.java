package com.dubatovka.app.controller;

import com.dubatovka.app.entity.Event;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_ERROR_MESSAGE;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_INFO_MESSAGE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_REQUEST_PARAMETER;


@FunctionalInterface
public interface Command {
    PageNavigator execute(HttpServletRequest request);
    
    default void validateRequestParams(MessageManager messageManager, String... params) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (!validatorService.isValidRequestParam(params)) {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_REQUEST_PARAMETER);
        }
    }
    
    default void setAndCheckEventNotNull(String eventIdStr, Event event, MessageManager messageManager) {
        if (messageManager.isErrMessEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService()) {
                Event eventDB = eventService.getEvent(eventIdStr);
                if (eventDB != null) {
                    event.setId(eventDB.getId());
                    event.setCategoryId(eventDB.getCategoryId());
                    event.setDate(eventDB.getDate());
                    event.setParticipant1(eventDB.getParticipant1());
                    event.setParticipant2(eventDB.getParticipant2());
                    event.setResult1(eventDB.getResult1());
                    event.setResult2(eventDB.getResult2());
                    event.setOutcomeSet(eventDB.getOutcomeSet());
                } else {
                    messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_ID);
                }
            }
        }
    }
    
    default void setMessagesToRequest(MessageManager messageManager, HttpServletRequest request) {
        String error = messageManager.getErrMessContent();
        String info = messageManager.getInfMessContent();
        if (!error.isEmpty()) {
            request.setAttribute(ATTR_ERROR_MESSAGE, error);
        }
        if (!info.isEmpty()) {
            request.setAttribute(ATTR_INFO_MESSAGE, info);
        }
    }
}
package com.dubatovka.app.controller;

import com.dubatovka.app.entity.Event;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.MessageService;
import com.dubatovka.app.service.ValidationService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import static com.dubatovka.app.config.ConfigConstant.ATTR_ERROR_MESSAGE;
import static com.dubatovka.app.config.ConfigConstant.ATTR_INFO_MESSAGE;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_REQUEST_PARAMETER;


@FunctionalInterface
public interface Command {
    PageNavigator execute(HttpServletRequest request);
    
    default void validateRequestParams(MessageService messageService, String... params) {
        ValidationService validationService = ServiceFactory.getValidationService();
        if (!validationService.isValidRequestParam(params)) {
            messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_REQUEST_PARAMETER);
        }
    }
    
    default void setAndCheckEventNotNull(String eventIdStr, Event event, MessageService messageService) {
        if (messageService.isErrMessEmpty()) {
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
                    messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_ID);
                }
            }
        }
    }
    
    default void setMessagesToRequest(MessageService messageService, HttpServletRequest request) {
        String error = messageService.getErrMessContent();
        String info = messageService.getInfMessContent();
        if (!error.isEmpty()) {
            request.setAttribute(ATTR_ERROR_MESSAGE, error);
        }
        if (!info.isEmpty()) {
            request.setAttribute(ATTR_INFO_MESSAGE, info);
        }
    }
}
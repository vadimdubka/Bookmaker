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
import java.time.LocalDateTime;

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
        
        String categoryIdStr = request.getParameter(PARAM_CATEGORY_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        String result1Str = request.getParameter(PARAM_RESULT_1);
        String result2Str = request.getParameter(PARAM_RESULT_2);
        String outcome1Str = request.getParameter(PARAM_OUTCOME_1);
        String outcomeXStr = request.getParameter(PARAM_OUTCOME_X);
        String outcome2Str = request.getParameter(PARAM_OUTCOME_2);
    
        //TODO разнести по разным командам, а не в одной все
        switch (editType) {
            case EVENT_EDIT_TYPE_DELETE:
                validateRequestParams(errorMessage, eventIdStr);
                if (errorMessage.toString().trim().isEmpty()) {
                    try (EventService eventService = ServiceFactory.getEventService()) {
                        validateDeleteEventCommand(errorMessage, eventIdStr);
                        if (errorMessage.toString().trim().isEmpty()) {
                            int eventId = Integer.parseInt(eventIdStr);
                            eventService.deleteEvent(eventId, errorMessage);
                            if (errorMessage.toString().trim().isEmpty()) {
                                request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_EVENT_DELETE_SUCCESS);
                            } else {
                                request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_EVENT_DELETE_FAIL);
                            }
                        }
                    }
                } else {
                    request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_INVALID_REQUEST_PARAMETER);
                }
                navigator = PageNavigator.FORWARD_PREV_QUERY;
                break;
            case EVENT_EDIT_TYPE_CREATE:
                validateRequestParams(errorMessage, categoryIdStr, dateTimeStr, participant1, participant2);
                if (errorMessage.toString().trim().isEmpty()) {
                    try (EventService eventService = ServiceFactory.getEventService()) {
                        validateCreateEventCommand(errorMessage, categoryIdStr, dateTimeStr, participant1, participant2);
                        if (errorMessage.toString().trim().isEmpty()) {
                            int categotyId = Integer.parseInt(categoryIdStr);
                            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
                            Event event = new Event();
                            event.setCategoryId(categotyId);
                            event.setDate(dateTime);
                            event.setParticipant1(participant1.trim());
                            event.setParticipant2(participant2.trim());
                            
                            eventService.insertEvent(event, errorMessage);
                            if (errorMessage.toString().trim().isEmpty()) {
                                request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_EVENT_CREATE_SUCCESS);
                            } else {
                                request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_EVENT_CREATE_FAIL);
                            }
                        }
                    }
                } else {
                    request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_INVALID_REQUEST_PARAMETER);
                }
                navigator = PageNavigator.FORWARD_PREV_QUERY;
                break;
            case EVENT_EDIT_TYPE_UPDATE_INFO:
                validateRequestParams(errorMessage, dateTimeStr, participant1, participant2);
                if (errorMessage.toString().trim().isEmpty()) {
                    try (EventService eventService = ServiceFactory.getEventService()) {
                        Event event = eventService.getEvent(eventIdStr);
                        validateEventNotNull(event, errorMessage);
                        validateUpdateEventCommand(errorMessage, dateTimeStr, participant1, participant2);
                        if (errorMessage.toString().trim().isEmpty()) {
                            event.setDate(LocalDateTime.parse(dateTimeStr));
                            event.setParticipant1(participant1.trim());
                            event.setParticipant2(participant2.trim());
                            
                            eventService.updateEvent(event, errorMessage);
                            if (errorMessage.toString().trim().isEmpty()) {
                                request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_EVENT_CREATE_SUCCESS);
                            } else {
                                request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_EVENT_CREATE_FAIL);
                            }
                        }
                    }
                } else {
                    request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_INVALID_REQUEST_PARAMETER);
                }
                navigator = PageNavigator.FORWARD_PREV_QUERY;
                break;
            case EVENT_EDIT_TYPE_UPDATE_RESULT:
                
                break;
            case EVENT_EDIT_TYPE_UPDATE_COEFFICIENT:
                
                break;
        }
        if (errorMessage.toString().trim().isEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService()) {
                Event event = eventService.getEvent(eventIdStr);
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_INVALID_REQUEST_PARAMETER);
        }
        navigator = PageNavigator.FORWARD_PREV_QUERY;
        
        return navigator;
    }
    
    private void validateCreateEventCommand(StringBuilder errorMessage, String categoryIdStr, String dateTimeStr, String participant1, String participant2) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (validatorService.isValidId(categoryIdStr)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_CATEGORY_ID).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidEventDateTime(dateTimeStr)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_DATE).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidEventParticipantName(participant1)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_PARTICIPANT).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidEventParticipantName(participant2)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_PARTICIPANT).append(MESSAGE_SEPARATOR);
        }
    }
    
    private void validateUpdateEventCommand(StringBuilder errorMessage, String dateTimeStr, String participant1, String participant2) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (errorMessage.toString().trim().isEmpty()) {
            if (validatorService.isValidEventDateTime(dateTimeStr)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_DATE).append(MESSAGE_SEPARATOR);
            }
            if (validatorService.isValidEventParticipantName(participant1)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_PARTICIPANT).append(MESSAGE_SEPARATOR);
            }
            if (validatorService.isValidEventParticipantName(participant2)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_PARTICIPANT).append(MESSAGE_SEPARATOR);
            }
        }
    }
    
    private void validateDeleteEventCommand(StringBuilder errorMessage, String eventIdStr) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (validatorService.isValidId(eventIdStr)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
        }
    }
    
    private void validateEventNotNull(Event event, StringBuilder errorMessage) {
        if (event == null) {
            errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
        }
    }
}

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

public class EventInfoUpdateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        
        //TODO запретить обновлять наименования событий для которых уже есть ставки
        validateRequestParams(errorMessage, eventIdStr, dateTimeStr, participant1, participant2);
        if (errorMessage.toString().trim().isEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService()) {
                validateCommand(errorMessage, eventIdStr, dateTimeStr, participant1, participant2);
                if (errorMessage.toString().trim().isEmpty()) {
                    Event event = eventService.getEvent(eventIdStr);
                    event.setDate(LocalDateTime.parse(dateTimeStr));
                    event.setParticipant1(participant1.trim());
                    event.setParticipant2(participant2.trim());
                    eventService.updateEventInfo(event, errorMessage);
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
    
    private void validateCommand(StringBuilder errorMessage, String eventIdStr, String dateTimeStr, String participant1, String participant2) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (!validatorService.isValidId(eventIdStr)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
        }
        if (!validatorService.isValidEventDateTime(dateTimeStr)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_DATE).append(MESSAGE_SEPARATOR);
        }
        if (!validatorService.isValidEventParticipantName(participant1)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_PARTICIPANT).append(MESSAGE_SEPARATOR);
        }
        if (!validatorService.isValidEventParticipantName(participant2)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_PARTICIPANT).append(MESSAGE_SEPARATOR);
        }
    }
}
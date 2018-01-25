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

public class EventCreateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String categoryIdStr = request.getParameter(PARAM_CATEGORY_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        
        validateRequestParams(errorMessage, categoryIdStr, dateTimeStr, participant1, participant2);
        validateCommand(errorMessage, categoryIdStr, dateTimeStr, participant1, participant2);
        if (errorMessage.toString().trim().isEmpty()) {
            int categoryId = Integer.parseInt(categoryIdStr);
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            Event event = new Event();
            event.setCategoryId(categoryId);
            event.setDate(dateTime);
            event.setParticipant1(participant1.trim());
            event.setParticipant2(participant2.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.insertEvent(event, errorMessage);
            }
            if (errorMessage.toString().trim().isEmpty()) {
                infoMessage.append(MESSAGE_INFO_EVENT_CREATE_SUCCESS).append(MESSAGE_SEPARATOR);
            } else {
                errorMessage.append(MESSAGE_ERROR_EVENT_CREATE_FAIL).append(MESSAGE_SEPARATOR);
            }
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(StringBuilder errorMessage, String categoryIdStr, String dateTimeStr, String participant1, String participant2) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(categoryIdStr)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_CATEGORY_ID).append(MESSAGE_SEPARATOR);
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
}

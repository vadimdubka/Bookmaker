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

import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_EVENT_UPDATE_INFO;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_DATE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_PARTICIPANT;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_EVENT_UPDATE_INFO;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_SEPARATOR;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_DATE;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PARTICIPANT_1;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PARTICIPANT_2;

public class EventInfoUpdateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        Event event = new Event();
        
        validateRequestParams(messageManager, errorMessage, eventIdStr, dateTimeStr, participant1, participant2);
        setAndCheckEventNotNull(eventIdStr, event, messageManager, errorMessage);
        validateCommand(messageManager, errorMessage, eventIdStr, dateTimeStr, participant1, participant2);
        if (errorMessage.toString().trim().isEmpty()) {
            event.setDate(LocalDateTime.parse(dateTimeStr));
            event.setParticipant1(participant1.trim());
            event.setParticipant2(participant2.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.updateEventInfo(event, messageManager, errorMessage);
            }
            if (errorMessage.toString().trim().isEmpty()) {
                infoMessage.append(messageManager.getMessageByKey(MESSAGE_INF_EVENT_UPDATE_INFO)).append(MESSAGE_SEPARATOR);
            } else {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_EVENT_UPDATE_INFO)).append(MESSAGE_SEPARATOR);
            }
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(MessageManager messageManager, StringBuilder errorMessage, String eventIdStr, String dateTimeStr, String participant1, String participant2) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(eventIdStr)) {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_EVENT_ID)).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidEventDateTime(dateTimeStr)) {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_DATE)).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidEventParticipantName(participant1)) {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_PARTICIPANT)).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidEventParticipantName(participant2)) {
                errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_PARTICIPANT)).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

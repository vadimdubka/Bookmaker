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
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_EVENT_CREATE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_CATEGORY_ID;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_DATE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_PARTICIPANT;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_EVENT_CREATE;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_CATEGORY_ID;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_DATE;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PARTICIPANT_1;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PARTICIPANT_2;

public class EventCreateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        
        String categoryIdStr = request.getParameter(PARAM_CATEGORY_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        
        validateRequestParams(messageManager, categoryIdStr, dateTimeStr, participant1, participant2);
        validateCommand(messageManager, categoryIdStr, dateTimeStr, participant1, participant2);
        if (messageManager.isErrMessEmpty()) {
            int categoryId = Integer.parseInt(categoryIdStr);
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            Event event = new Event();
            event.setCategoryId(categoryId);
            event.setDate(dateTime);
            event.setParticipant1(participant1.trim());
            event.setParticipant2(participant2.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.insertEvent(event, messageManager);
            }
            if (messageManager.isErrMessEmpty()) {
                messageManager.appendInfMessByKey(MESSAGE_INF_EVENT_CREATE);
            } else {
                messageManager.appendErrMessByKey(MESSAGE_ERR_EVENT_CREATE);
            }
        }
        setMessagesToRequest(messageManager, request);
        return navigator;
    }
    
    private void validateCommand(MessageManager messageManager, String categoryIdStr, String dateTimeStr, String participant1, String participant2) {
        if (messageManager.isErrMessEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(categoryIdStr)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_CATEGORY_ID);
            }
            if (!validatorService.isValidEventDateTime(dateTimeStr)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_DATE);
            }
            if (!validatorService.isValidEventParticipantName(participant1) || !validatorService.isValidEventParticipantName(participant2)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_PARTICIPANT);
            }
        }
    }
}

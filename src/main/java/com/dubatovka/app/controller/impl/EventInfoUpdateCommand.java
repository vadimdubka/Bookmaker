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
import java.time.LocalDateTime;

import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_EVENT_UPDATE_INFO;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_DATE;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_PARTICIPANT;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_INF_EVENT_UPDATE_INFO;
import static com.dubatovka.app.config.ConfigConstant.PARAM_DATE;
import static com.dubatovka.app.config.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.config.ConfigConstant.PARAM_PARTICIPANT_1;
import static com.dubatovka.app.config.ConfigConstant.PARAM_PARTICIPANT_2;

/**
 * The class provides command implementation for event information update.
 *
 * @author Dubatovka Vadim
 */
public class EventInfoUpdateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        MessageService messageService = ServiceFactory.getMessageService(session);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        Event event = new Event();
        
        validateRequestParams(messageService, eventIdStr, dateTimeStr, participant1, participant2);
        setAndCheckEventNotNull(eventIdStr, event, messageService);
        validateCommand(messageService, eventIdStr, dateTimeStr, participant1, participant2);
        if (messageService.isErrMessEmpty()) {
            event.setDate(LocalDateTime.parse(dateTimeStr));
            event.setParticipant1(participant1.trim());
            event.setParticipant2(participant2.trim());
            try (EventService eventService = ServiceFactory.getEventService()) {
                eventService.updateEventInfo(event, messageService);
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
    
    private void validateCommand(MessageService messageService, String eventIdStr, String dateTimeStr, String participant1, String participant2) {
        if (messageService.isErrMessEmpty()) {
            ValidationService validationService = ServiceFactory.getValidationService();
            if (!validationService.isValidId(eventIdStr)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_ID);
            }
            if (!validationService.isValidEventDateTime(dateTimeStr)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_DATE);
            }
            if (!validationService.isValidEventParticipantName(participant1) ||
                        !validationService.isValidEventParticipantName(participant2)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_PARTICIPANT);
            }
        }
    }
}

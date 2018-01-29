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
    /**
     * Method provides process for event information update.<p>Takes input parameters and attributes
     * from {@link HttpServletRequest} and {@link HttpSession} and based on them update event
     * information.</p>
     *
     * @param request {@link HttpServletRequest} from client
     * @return {@link PageNavigator#FORWARD_PREV_QUERY}
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MessageService messageService = ServiceFactory.getMessageService(session);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String dateTimeStr = request.getParameter(PARAM_DATE);
        String participant1 = request.getParameter(PARAM_PARTICIPANT_1);
        String participant2 = request.getParameter(PARAM_PARTICIPANT_2);
        Event event = new Event();
        
        validateRequestParams(messageService, eventIdStr, dateTimeStr, participant1, participant2);
        checkAndSetEventNotNull(eventIdStr, event, messageService);
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
        return PageNavigator.FORWARD_PREV_QUERY;
    }
    
    /**
     * Method validates parameters using {@link ValidationService} to confirm that all necessary
     * parameters for command execution have proper state according to requirements for application.
     *
     * @param messageService {@link MessageService} to hold message about validation result
     * @param eventIdStr     {@link String} parameter for validation
     * @param dateTimeStr    {@link String} parameter for validation
     * @param participant1   {@link String} parameter for validation
     * @param participant2   {@link String} parameter for validation
     */
    private void validateCommand(MessageService messageService, String eventIdStr,
                                 String dateTimeStr, String participant1, String participant2) {
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

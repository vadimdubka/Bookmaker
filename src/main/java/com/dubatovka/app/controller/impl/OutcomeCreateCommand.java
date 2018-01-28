package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.service.MessageService;
import com.dubatovka.app.service.OutcomeService;
import com.dubatovka.app.service.ValidationService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_OUTCOME;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_OUTCOME_UPDATE;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_INF_OUTCOME_UPDATE;
import static com.dubatovka.app.config.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.config.ConfigConstant.PARAM_OUTCOME_1;
import static com.dubatovka.app.config.ConfigConstant.PARAM_OUTCOME_2;
import static com.dubatovka.app.config.ConfigConstant.PARAM_OUTCOME_X;

public class OutcomeCreateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        MessageService messageService = ServiceFactory.getMessageService(session);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcome1Str = request.getParameter(PARAM_OUTCOME_1);
        String outcomeXStr = request.getParameter(PARAM_OUTCOME_X);
        String outcome2Str = request.getParameter(PARAM_OUTCOME_2);
        Event event = new Event();
        
        validateRequestParams(messageService, outcome1Str, outcomeXStr, outcome2Str);
        setAndCheckEventNotNull(eventIdStr, event, messageService);
        validateCommand(messageService, outcome1Str, outcomeXStr, outcome2Str);
        if (messageService.isErrMessEmpty()) {
            int eventId = event.getId();
            Outcome outcomeType1 = new Outcome(eventId, new BigDecimal(outcome1Str), Outcome.Type.TYPE_1);
            Outcome outcomeTypeX = new Outcome(eventId, new BigDecimal(outcomeXStr), Outcome.Type.TYPE_X);
            Outcome outcomeType2 = new Outcome(eventId, new BigDecimal(outcome2Str), Outcome.Type.TYPE_2);
            Set<Outcome> outcomeSet = new HashSet<>(3);
            outcomeSet.add(outcomeType1);
            outcomeSet.add(outcomeTypeX);
            outcomeSet.add(outcomeType2);
            try (OutcomeService outcomeService = ServiceFactory.getOutcomeService()) {
                outcomeService.insertOutcomeSet(outcomeSet, messageService);
            }
            if (messageService.isErrMessEmpty()) {
                messageService.appendInfMessByKey(MESSAGE_INF_OUTCOME_UPDATE);
            } else {
                messageService.appendErrMessByKey(MESSAGE_ERR_OUTCOME_UPDATE);
            }
        }
        
        setMessagesToRequest(messageService, request);
        return navigator;
    }
    
    private void validateCommand(MessageService messageService, String outcome1Str, String outcomeXStr, String outcome2Str) {
        if (messageService.isErrMessEmpty()) {
            ValidationService validationService = ServiceFactory.getValidationService();
            if (!validationService.isValidOutcomeCoeff(outcome1Str)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_OUTCOME);
            }
            if (!validationService.isValidOutcomeCoeff(outcomeXStr)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_OUTCOME);
            }
            if (!validationService.isValidOutcomeCoeff(outcome2Str)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_OUTCOME);
            }
        }
    }
}

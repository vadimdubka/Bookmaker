package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.OutcomeService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class OutcomeCreateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcome1Str = request.getParameter(PARAM_OUTCOME_1);
        String outcomeXStr = request.getParameter(PARAM_OUTCOME_X);
        String outcome2Str = request.getParameter(PARAM_OUTCOME_2);
        Event event = new Event();
        
        validateRequestParams(errorMessage, outcome1Str, outcomeXStr, outcome2Str);
        setAndCheckEventNotNull(eventIdStr, event, errorMessage);
        validateCommand(errorMessage, outcome1Str, outcomeXStr, outcome2Str);
        if (errorMessage.toString().trim().isEmpty()) {
            int eventId = event.getId();
            Outcome outcomeType1 = new Outcome(eventId, new BigDecimal(outcome1Str), Outcome.Type.TYPE_1);
            Outcome outcomeTypeX = new Outcome(eventId, new BigDecimal(outcomeXStr), Outcome.Type.TYPE_X);
            Outcome outcomeType2 = new Outcome(eventId, new BigDecimal(outcome2Str), Outcome.Type.TYPE_2);
            Set<Outcome> outcomeSet = new HashSet<>(3);
            outcomeSet.add(outcomeType1);
            outcomeSet.add(outcomeTypeX);
            outcomeSet.add(outcomeType2);
            try (OutcomeService outcomeService = ServiceFactory.getOutcomeService()) {
                outcomeService.insertOutcomeSet(outcomeSet, errorMessage);
            }
            if (errorMessage.toString().trim().isEmpty()) {
                infoMessage.append(MESSAGE_INFO_OUTCOME_UPDATE_SUCCESS).append(MESSAGE_SEPARATOR);
            } else {
                errorMessage.append(MESSAGE_ERR_OUTCOME_UPDATE_FAIL).append(MESSAGE_SEPARATOR);
            }
        }
    
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(StringBuilder errorMessage, String outcome1Str, String outcomeXStr, String outcome2Str) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidOutcomeCoeff(outcome1Str)) {
                errorMessage.append(MESSAGE_ERR_INVALID_EVENT_OUTCOME).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidOutcomeCoeff(outcomeXStr)) {
                errorMessage.append(MESSAGE_ERR_INVALID_EVENT_OUTCOME).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidOutcomeCoeff(outcome2Str)) {
                errorMessage.append(MESSAGE_ERR_INVALID_EVENT_OUTCOME).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

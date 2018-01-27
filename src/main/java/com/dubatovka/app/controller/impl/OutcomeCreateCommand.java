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

import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_OUTCOME;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_OUTCOME_UPDATE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_OUTCOME_UPDATE;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_OUTCOME_1;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_OUTCOME_2;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_OUTCOME_X;

public class OutcomeCreateCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcome1Str = request.getParameter(PARAM_OUTCOME_1);
        String outcomeXStr = request.getParameter(PARAM_OUTCOME_X);
        String outcome2Str = request.getParameter(PARAM_OUTCOME_2);
        Event event = new Event();
        
        validateRequestParams(messageManager, outcome1Str, outcomeXStr, outcome2Str);
        setAndCheckEventNotNull(eventIdStr, event, messageManager);
        validateCommand(messageManager, outcome1Str, outcomeXStr, outcome2Str);
        if (messageManager.isErrMessEmpty()) {
            int eventId = event.getId();
            Outcome outcomeType1 = new Outcome(eventId, new BigDecimal(outcome1Str), Outcome.Type.TYPE_1);
            Outcome outcomeTypeX = new Outcome(eventId, new BigDecimal(outcomeXStr), Outcome.Type.TYPE_X);
            Outcome outcomeType2 = new Outcome(eventId, new BigDecimal(outcome2Str), Outcome.Type.TYPE_2);
            Set<Outcome> outcomeSet = new HashSet<>(3);
            outcomeSet.add(outcomeType1);
            outcomeSet.add(outcomeTypeX);
            outcomeSet.add(outcomeType2);
            try (OutcomeService outcomeService = ServiceFactory.getOutcomeService()) {
                outcomeService.insertOutcomeSet(outcomeSet, messageManager);
            }
            if (messageManager.isErrMessEmpty()) {
                messageManager.appendInfMessByKey(MESSAGE_INF_OUTCOME_UPDATE);
            } else {
                messageManager.appendErrMessByKey(MESSAGE_ERR_OUTCOME_UPDATE);
            }
        }
        
        setMessagesToRequest(messageManager, request);
        return navigator;
    }
    
    private void validateCommand(MessageManager messageManager, String outcome1Str, String outcomeXStr, String outcome2Str) {
        if (messageManager.isErrMessEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidOutcomeCoeff(outcome1Str)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_OUTCOME);
            }
            if (!validatorService.isValidOutcomeCoeff(outcomeXStr)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_OUTCOME);
            }
            if (!validatorService.isValidOutcomeCoeff(outcome2Str)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_OUTCOME);
            }
        }
    }
}

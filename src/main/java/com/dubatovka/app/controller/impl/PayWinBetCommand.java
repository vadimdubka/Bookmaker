package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class PayWinBetCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        validateRequestParams(errorMessage, eventIdStr);
        validateCommand(errorMessage, eventIdStr);
        if (errorMessage.toString().trim().isEmpty()) {
            int eventId = Integer.parseInt(eventIdStr);
            try (BetService betService = ServiceFactory.getBetService()) {
                betService.payWinBet(eventId, errorMessage);
            }
            if (errorMessage.toString().trim().isEmpty()) {
                infoMessage.append(MESSAGE_INFO_PAY_WIN_BET_SUCCESS).append(MESSAGE_SEPARATOR);
            } else {
                errorMessage.append(MESSAGE_ERROR_PAY_WIN_BET_FAIL).append(MESSAGE_SEPARATOR);
            }
        }
    
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(StringBuilder errorMessage, String eventIdStr) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(eventIdStr)) {
                errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

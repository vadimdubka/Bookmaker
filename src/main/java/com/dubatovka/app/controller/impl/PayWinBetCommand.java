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
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        validateRequestParams(errorMessage, eventIdStr);
        if (errorMessage.toString().trim().isEmpty()) {
            try (BetService betService = ServiceFactory.getBetService()) {
                validateCommand(errorMessage, eventIdStr);
                if (errorMessage.toString().trim().isEmpty()) {
                    int eventId = Integer.parseInt(eventIdStr);
                    betService.payWinBet(eventId, errorMessage);
                    if (errorMessage.toString().trim().isEmpty()) {
                        request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_PAY_WIN_BET_SUCCESS);
                    } else {
                        request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_PAY_WIN_BET_FAIL);
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
    
    private void validateCommand(StringBuilder errorMessage, String eventIdStr) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (!validatorService.isValidId(eventIdStr)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
        }
    }
    
    
}

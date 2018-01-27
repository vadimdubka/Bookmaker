package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_PAY_WIN_BET;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_PAY_WIN_BET;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EVENT_ID;

public class PayWinBetCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        validateRequestParams(messageManager, eventIdStr);
        validateCommand(messageManager, eventIdStr);
        if (messageManager.isErrMessEmpty()) {
            int eventId = Integer.parseInt(eventIdStr);
            try (BetService betService = ServiceFactory.getBetService()) {
                betService.payWinBet(eventId, messageManager);
            }
            if (messageManager.isErrMessEmpty()) {
                messageManager.appendInfMessByKey(MESSAGE_INF_PAY_WIN_BET);
            } else {
                messageManager.appendErrMessByKey(MESSAGE_ERR_PAY_WIN_BET);
            }
        }
        
        setMessagesToRequest(messageManager, request);
        return navigator;
    }
    
    
    private void validateCommand(MessageManager messageManager, String eventIdStr) {
        if (messageManager.isErrMessEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidId(eventIdStr)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_ID);
            }
        }
    }
}

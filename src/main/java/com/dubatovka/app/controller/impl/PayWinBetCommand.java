package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.MessageService;
import com.dubatovka.app.service.ValidationService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_EVENT_ID;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_PAY_WIN_BET;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_INF_PAY_WIN_BET;
import static com.dubatovka.app.config.ConfigConstant.PARAM_EVENT_ID;

/**
 * The class provides command implementation for winning bets payment.
 *
 * @author Dubatovka Vadim
 */
public class PayWinBetCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        MessageService messageService = ServiceFactory.getMessageService(session);
        
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        validateRequestParams(messageService, eventIdStr);
        validateCommand(messageService, eventIdStr);
        if (messageService.isErrMessEmpty()) {
            int eventId = Integer.parseInt(eventIdStr);
            try (BetService betService = ServiceFactory.getBetService()) {
                betService.payWinBet(eventId, messageService);
            }
            if (messageService.isErrMessEmpty()) {
                messageService.appendInfMessByKey(MESSAGE_INF_PAY_WIN_BET);
            } else {
                messageService.appendErrMessByKey(MESSAGE_ERR_PAY_WIN_BET);
            }
        }
        
        setMessagesToRequest(messageService, request);
        return navigator;
    }
    
    /**
     * Method validates parameters using {@link ValidationService} to confirm that all necessary
     * parameters for command execution have proper state according to requirements for application.
     *
     * @param messageService {@link MessageService} to hold message about validation result
     * @param eventIdStr     {@link String} parameter for validation
     */
    private void validateCommand(MessageService messageService, String eventIdStr) {
        if (messageService.isErrMessEmpty()) {
            ValidationService validationService = ServiceFactory.getValidationService();
            if (!validationService.isValidId(eventIdStr)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EVENT_ID);
            }
        }
    }
}

package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.PlayerStatus;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_PLAYER;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_ROLE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_BETTING_INTERRUPTED;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_BET_AMOUNT_LESS_BALANCE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_BET_AMOUNT_LESS_BET_LIMIT;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_BET_FOR_EMPLOYEE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_BET_GOTO_REGISTRATION;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_BET_TIME;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_BET_AMOUNT;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_OUTCOME_COEFF_CHANGE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_PLAYER_STATUS_BAN;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_INF_BET_IS_DONE;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_BET_AMOUNT;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_OUTCOME_COEFFICIENT;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_OUTCOME_TYPE;
import static com.dubatovka.app.manager.ConfigConstant.PLAYER;

public class MakeBetCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        
        Player player = (Player) session.getAttribute(PLAYER);
        User.UserRole role = (User.UserRole) session.getAttribute(ATTR_ROLE);
        String betAmountStr = request.getParameter(PARAM_BET_AMOUNT);
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
        String outcomeCoeffOnPage = request.getParameter(PARAM_OUTCOME_COEFFICIENT);
        Event event = new Event();
        
        validateRequestParams(messageManager, betAmountStr, eventIdStr, outcomeType, outcomeCoeffOnPage);
        setAndCheckEventNotNull(eventIdStr, event, messageManager);
        validateUserRole(role, messageManager);
        validateCommand(player, betAmountStr, event, outcomeType, outcomeCoeffOnPage, messageManager);
        if (messageManager.isErrMessEmpty()) {
            try (PlayerService playerService = ServiceFactory.getPlayerService();
                 BetService betService = ServiceFactory.getBetService()) {
                BigDecimal coefficient = event.getOutcomeByType(outcomeType).getCoefficient();
                BigDecimal betAmount = new BigDecimal(betAmountStr);
                Bet bet = new Bet(player.getId(), event.getId(), outcomeType, LocalDateTime.now(),
                                         coefficient, betAmount, Bet.Status.NEW);
                betService.makeBet(bet, messageManager);
                if (messageManager.isErrMessEmpty()) {
                    playerService.updatePlayerInfo(player);
                    session.setAttribute(ATTR_PLAYER, player);
                    navigator = PageNavigator.FORWARD_GOTO_MAIN;
                    messageManager.appendInfMessByKey(MESSAGE_INF_BET_IS_DONE);
                } else {
                    messageManager.appendErrMessByKey(MESSAGE_ERR_BETTING_INTERRUPTED);
                }
            }
        }
        setMessagesToRequest(messageManager, request);
        return navigator;
    }
    
    private void validateUserRole(User.UserRole role, MessageManager messageManager) {
        if (messageManager.isErrMessEmpty()) {
            if (role == User.UserRole.GUEST) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_BET_GOTO_REGISTRATION);
            } else if ((role == User.UserRole.ADMIN) || (role == User.UserRole.ANALYST)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_BET_FOR_EMPLOYEE);
            }
        }
    }
    
    private void validateCommand(Player player, String betAmountStr, Event event, String outcomeType, String outcomeCoeffOnPage, MessageManager messageManager) {
        if (messageManager.isErrMessEmpty()) {
            LocalDateTime betDateTime = LocalDateTime.now();
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            if (!validatorService.isValidBetTime(betDateTime, event.getDate())) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_BET_TIME);
            }
            if (!validatorService.isValidOutcomeCoeffOnPage(outcomeCoeffOnPage, event, outcomeType)) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_OUTCOME_COEFF_CHANGE);
            }
            
            if (player.getAccount().getStatus().getStatus() == PlayerStatus.Status.BAN) {
                messageManager.appendErrMessByKey(MESSAGE_ERR_PLAYER_STATUS_BAN);
            }
            if (validatorService.isValidBetAmount(betAmountStr)) {
                BigDecimal betAmount = new BigDecimal(betAmountStr);
                BigDecimal balance = player.getAccount().getBalance();
                BigDecimal betLimit = player.getAccount().getStatus().getBetLimit();
                if (betAmount.compareTo(balance) > 0) {
                    messageManager.appendErrMessByKey(MESSAGE_ERR_BET_AMOUNT_LESS_BALANCE);
                }
                if (betAmount.compareTo(betLimit) >= 0) {
                    messageManager.appendErrMessByKey(MESSAGE_ERR_BET_AMOUNT_LESS_BET_LIMIT);
                }
            } else {
                messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_BET_AMOUNT);
            }
        }
    }
}

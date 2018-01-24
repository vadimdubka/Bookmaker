package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.*;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class MakeBetCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        Player player = (Player) session.getAttribute(PLAYER);
        User.UserRole role = (User.UserRole) session.getAttribute(ATTR_ROLE);
        String betAmountStr = request.getParameter(PARAM_BET_AMOUNT);
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
        String outcomeCoeffOnPage = request.getParameter(PARAM_OUTCOME_COEFFICIENT);
        Event event = new Event();
        
        validateRequestParams(errorMessage, betAmountStr, eventIdStr, outcomeType, outcomeCoeffOnPage);
        setAndCheckEventNotNull(eventIdStr, event, errorMessage);
        validateUserRole(role, errorMessage);
        validateCommand(role, player, betAmountStr, event, outcomeType, outcomeCoeffOnPage, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            try (PlayerService playerService = ServiceFactory.getPlayerService(); BetService betService = ServiceFactory.getBetService()) {
                BigDecimal coefficient = event.getOutcomeByType(outcomeType).getCoefficient();
                BigDecimal betAmount = new BigDecimal(betAmountStr);
                Bet bet = new Bet(player.getId(), event.getId(), outcomeType, LocalDateTime.now(), coefficient, betAmount, Bet.Status.NEW);
                betService.makeBet(bet, errorMessage);
                if (errorMessage.toString().trim().isEmpty()) {
                    playerService.updatePlayerInfo(player);
                    session.setAttribute(ATTR_PLAYER, player);
                    navigator = PageNavigator.FORWARD_GOTO_MAIN;
                    infoMessage.append(MESSAGE_INFO_BET_IS_DONE).append(MESSAGE_SEPARATOR);
                } else {
                    errorMessage.append(MESSAGE_ERROR_BETTING_INTERRUPTED).append(MESSAGE_SEPARATOR);
                }
            }
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private void validateCommand(User.UserRole role, Player player, String betAmountStr, Event event, String outcomeType, String outcomeCoeffOnPage, StringBuilder errorMessage) {
        if (errorMessage.toString().trim().isEmpty()) {
            LocalDateTime betDateTime = LocalDateTime.now();
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            
            if (!validatorService.isValidBetTime(betDateTime, event)) {
                errorMessage.append(MESSAGE_ERROR_BET_TIME).append(MESSAGE_SEPARATOR);
            }
            
            if (!validatorService.isValidOutcomeCoeffOnPage(outcomeCoeffOnPage, event, outcomeType)) {
                errorMessage.append(MESSAGE_ERROR_OUTCOME_COEFF).append(MESSAGE_SEPARATOR);
            }
    
            if (player.getAccount().getStatus().getStatus() == PlayerStatus.Status.BAN) {
                errorMessage.append(MESSAGE_ERROR_PLAYER_STATUS_BAN).append(MESSAGE_SEPARATOR);
            }
            
            if (validatorService.isValidBetAmount(betAmountStr)) {
                BigDecimal betAmount = new BigDecimal(betAmountStr);
                BigDecimal balance = player.getAccount().getBalance();
                BigDecimal betLimit = player.getAccount().getStatus().getBetLimit();
                if (betAmount.compareTo(balance) > 0) {
                    errorMessage.append(MESSAGE_ERROR_BET_AMOUNT_LESS_BALANCE).append(MESSAGE_SEPARATOR);
                }
                if (betAmount.compareTo(betLimit) >= 0) {
                    errorMessage.append(MESSAGE_ERROR_BET_AMOUNT_LESS_BET_LIMIT).append(MESSAGE_SEPARATOR);
                }
            } else {
                errorMessage.append(MESSAGE_ERROR_BET_AMOUNT_INVALID).append(MESSAGE_SEPARATOR);
            }
            
            
        }
    }
    
    private void validateUserRole(User.UserRole role, StringBuilder errorMessage) {
        if (errorMessage.toString().trim().isEmpty()) {
            if (role == User.UserRole.GUEST) {
                errorMessage.append(MESSAGE_ERROR_BET_GOTO_REGISTRATION).append(MESSAGE_SEPARATOR);
            } else if ((role == User.UserRole.ADMIN) || (role == User.UserRole.ANALYST)) {
                errorMessage.append(MESSAGE_ERROR_BET_FOR_EMPLOYEE).append(MESSAGE_SEPARATOR);
            }
        }
    }
}

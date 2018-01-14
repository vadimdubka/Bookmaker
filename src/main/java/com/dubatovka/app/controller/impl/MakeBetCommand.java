package com.dubatovka.app.controller.impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class MakeBetCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        Player player = (Player) session.getAttribute(PLAYER);
        User.UserRole role = (User.UserRole) session.getAttribute(ATTR_ROLE);
        
        String betAmountStr = request.getParameter(PARAM_BET_AMOUNT);
        String eventIdStr = request.getParameter(PARAM_EVENT_ID);
        String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
        String outcomeCoeffOnPage = request.getParameter(PARAM_OUTCOME_COEFFICIENT);
        validateRequestParams(errorMessage, betAmountStr, eventIdStr, outcomeType, outcomeCoeffOnPage);
        if (errorMessage.toString().trim().isEmpty()) {
            try (EventService eventService = ServiceFactory.getEventService(); PlayerService playerService = ServiceFactory.getPlayerService()) {
                Event event = eventService.getEvent(eventIdStr);
                validateCommand(role, player, betAmountStr, event, outcomeType, outcomeCoeffOnPage, errorMessage);
                navigator = makeBet(request, session, errorMessage, player, betAmountStr, outcomeType, playerService, event);
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_INVALID_REQUEST_PARAMETER);
            navigator = PageNavigator.FORWARD_PREV_QUERY;
        }
        
        return navigator;
    }
    
    private PageNavigator makeBet(ServletRequest request, HttpSession session, StringBuilder errorMessage, Player player, String betAmountStr, String outcomeType, PlayerService playerService, Event event) {
        PageNavigator navigator;
        if (errorMessage.toString().trim().isEmpty()) {
            BigDecimal coefficient = event.getOutcomeByType(outcomeType).getCoefficient();
            BigDecimal betAmount = new BigDecimal(betAmountStr);
            Bet bet = new Bet(player.getId(), event.getId(), outcomeType, LocalDateTime.now(), coefficient, betAmount, Bet.Status.NEW);
            playerService.makeBet(bet, errorMessage);
            if (errorMessage.toString().trim().isEmpty()) {
                playerService.updatePlayerInfo(player);
                session.setAttribute(ATTR_PLAYER, player);
                request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_BET_IS_DONE);
                navigator = PageNavigator.FORWARD_GOTO_MAIN;
            } else {
                request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_BETTING_INTERRUPTED);
                navigator = PageNavigator.FORWARD_PREV_QUERY;
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage.toString().trim());
            navigator = PageNavigator.FORWARD_PREV_QUERY;
        }
        return navigator;
    }
    
    private void validateCommand(User.UserRole role, Player player, String betAmountStr, Event event, String outcomeType, String outcomeCoeffOnPage, StringBuilder errorMessage) {
        validateUserRole(role, errorMessage);
        validateEvent(event, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            validateMakeBetParams(errorMessage, player, betAmountStr, event, outcomeType, outcomeCoeffOnPage);
        }
    }
    
    private void validateUserRole(User.UserRole role, StringBuilder errorMessage) {
        if (role == User.UserRole.GUEST) {
            errorMessage.append(MESSAGE_ERROR_BET_GOTO_REGISTRATION).append(MESSAGE_SEPARATOR);
        } else if ((role == User.UserRole.ADMIN) || (role == User.UserRole.ANALYST)) {
            errorMessage.append(MESSAGE_ERROR_BET_FOR_EMPLOYEE).append(MESSAGE_SEPARATOR);
        }
    }
    
    private void validateEvent(Event event, StringBuilder errorMessage) {
        if (event == null) {
            errorMessage.append(MESSAGE_ERROR_EVENT_INVALID_ID).append(MESSAGE_SEPARATOR);
        }
    }
    
    private void validateMakeBetParams(StringBuilder errorMessage, Player player, String betAmountStr, Event event, String outcomeType, String outcomeCoeffOnPage) {
        LocalDateTime betDateTime = LocalDateTime.now();
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        
        if (!validatorService.isValidBetTime(betDateTime, event)) {
            errorMessage.append(MESSAGE_ERROR_BET_TIME).append(MESSAGE_SEPARATOR);
        }
        
        if (!validatorService.isValidOutcomeCoeffOnPage(outcomeCoeffOnPage, event, outcomeType)) {
            errorMessage.append(MESSAGE_ERROR_OUTCOME_COEFF).append(MESSAGE_SEPARATOR);
        }
        
        if (validatorService.isValidBetAmount(betAmountStr)) {
            BigDecimal betAmount = new BigDecimal(betAmountStr);
            BigDecimal balance = player.getAccount().getBalance();
            if (betAmount.compareTo(balance) > 0) {
                errorMessage.append(MESSAGE_ERROR_BET_AMOUNT_LESS_BALANCE).append(MESSAGE_SEPARATOR);
            }
        } else {
            errorMessage.append(MESSAGE_ERROR_BET_AMOUNT_INVALID).append(MESSAGE_SEPARATOR);
        }
    }
}

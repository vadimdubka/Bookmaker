package com.dubatovka.app.controller.command_impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.Transaction;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class MakeBetCommand implements Command {
    public static final String PARAM_EVENT_ID = "event_id";
    public static final String PARAM_OUTCOME_TYPE = "outcome_type";
    public static final String PARAM_OUTCOME_COEFFICIENT = "outcome_coefficient";
    public static final String PARAM_BET_AMOUNT = "bet_amount";
    //TODO сделать мультиязычным
    public static final String MESSAGE_ERROR_BET_GOTO_REGISTRATION = "Чтобы сделать ставку, пожалуйста, зарегистрируйтесь и войдите в систему";
    public static final String MESSAGE_ERROR_BET_FOR_EMPLOYEE = "Сотрудники букмекерской компании не могут делать ставки.";
    public static final String MESSAGE_ERROR_BET_AMOUNT_INVALID = "Введенное число не является валидным. Число должно быть больше нуля, меньше либо равно 999.99, число может быть дробным и содержать два знака после запятой.";
    public static final String MESSAGE_ERROR_BET_AMOUNT_LESS_BALANCE = "Введенная сумма ставки превышает баланс на счету игрока. Сделайте ставку меньше.";
    public static final String MESSAGE_ERROR_OUTCOME_COEFF = "Коэффициет исхода по выбранному событию изменился с тех пор, как вы решили сделать ставку. Проверьте выбранный коэффициент еще раз.";
    public static final String MESSAGE_ERROR_BET_TIME = "Время, отведенное на ставку, истекло.";
    public static final String MESSAGE_ERROR_BETTING_INTERRUPTED = "Ставка была прервана в процессе осуществления. Вероятно, уже сделана ставка на аналогичный исход события.";
    public static final String MESSAGE_INFO_BET_IS_DONE = "Ставка сделана!";
    
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
        
        Event event;
        try (EventService eventService = ServiceFactory.getEventService()) {
            event = eventService.getEventById(Integer.parseInt(eventIdStr));
        }
        
        validateParams(role, player, betAmountStr, event, outcomeType, outcomeCoeffOnPage, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            try (PlayerService playerService = ServiceFactory.getPlayerService()) {
                int playerId = player.getId();
                int eventId = event.getId();
                BigDecimal amount = event.getOutcomeByType(outcomeType).getCoefficient();
                BigDecimal betAmount = new BigDecimal(betAmountStr);
                playerService.makeBet(playerId, eventId, outcomeType, amount, betAmount, Transaction.TransactionType.WITHDRAW, errorMessage);
                if (errorMessage.toString().trim().isEmpty()) {
                    playerService.updatePlayerInfo(player);
                    session.setAttribute(ATTR_PLAYER, player);
                    request.setAttribute(ATTR_INFO_MESSAGE, MESSAGE_INFO_BET_IS_DONE);
                    navigator = PageNavigator.FORWARD_GOTO_PLAYER_STATE;
                } else {
                    request.setAttribute(ATTR_ERROR_MESSAGE, MESSAGE_ERROR_BETTING_INTERRUPTED);
                    navigator = PageNavigator.FORWARD_PREV_QUERY;
                }
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage.toString().trim());
            navigator = PageNavigator.FORWARD_PREV_QUERY;
        }
        
        return navigator;
    }
    
    private String validateParams(User.UserRole role, Player player, String betAmountStr, Event event, String outcomeType, String outcomeCoeffOnPage, StringBuilder errorMessage) {
        validateUserRole(role, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            validateMakeBetParams(errorMessage, player, betAmountStr, event, outcomeType, outcomeCoeffOnPage);
        }
        
        return errorMessage.toString().trim();
    }
    
    private void validateUserRole(User.UserRole role, StringBuilder errorMessage) {
        if (role == User.UserRole.GUEST) {
            errorMessage.append(MESSAGE_ERROR_BET_GOTO_REGISTRATION).append(MESSAGE_SEPARATOR);
        } else if ((role == User.UserRole.ADMIN) || (role == User.UserRole.ANALYST)) {
            errorMessage.append(MESSAGE_ERROR_BET_FOR_EMPLOYEE).append(MESSAGE_SEPARATOR);
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

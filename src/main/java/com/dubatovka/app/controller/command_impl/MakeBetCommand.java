package com.dubatovka.app.controller.command_impl;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;
import com.dubatovka.app.service.ValidatorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public static final String MESSAGE_ERROR_BET_AMOUNT = "Введенное число не является валидным. Число должно быть больше нуля, меньше либо равно 999.99, число может быть дробным и содержать два знака после запятой.";
    public static final String MESSAGE_ERROR_OUTCOME_COEFF = "Коэффициет исхода по выбранному событию изменился с тех пор, как вы решили сделать ставку. Проверьте выбранный коэффициент еще раз.";
    public static final String MESSAGE_ERROR_BET_TIME = "Время, отведенное на ставку, истекло.";
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        String errorMessage;
        
        HttpSession session = request.getSession();
        User.UserRole role = (User.UserRole) session.getAttribute(ATTR_ROLE);
        
        if (role == User.UserRole.PLAYER) {
            errorMessage = validateRequestParams(request);
            if (errorMessage.isEmpty()) {
                makeBet(request);
                navigator = PageNavigator.FORWARD_GOTO_MAIN;
            } else {
                navigator = PageNavigator.FORWARD_PREV_QUERY;
            }
            
            
            Player player = (Player) session.getAttribute(PLAYER);
            int playerId = player.getId();
            String eventId = request.getParameter(PARAM_EVENT_ID);
            String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
            String outcomeCoeffOnPage = request.getParameter(PARAM_OUTCOME_COEFFICIENT);
            String betAmount = request.getParameter(PARAM_BET_AMOUNT);
            
        } else if (role == User.UserRole.GUEST) {
            errorMessage = MESSAGE_ERROR_BET_GOTO_REGISTRATION;
            navigator = PageNavigator.FORWARD_PREV_QUERY;
        } else {
            errorMessage = MESSAGE_ERROR_BET_FOR_EMPLOYEE;
            navigator = PageNavigator.FORWARD_PREV_QUERY;
        }
        
        request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage);
        
        return navigator;
    }
    
    private void makeBet(HttpServletRequest request) {
        
    }
    
    private String validateRequestParams(HttpServletRequest request) {
        StringBuilder errorMessage;
        LocalDateTime betDateTime;
        String betAmount;
        Event event;
        try (EventService eventService = ServiceFactory.getEventService()) {
            HttpSession session = request.getSession();
            String locale = (String) session.getAttribute(ATTR_LOCALE);
            MessageManager messageManager = MessageManager.getMessageManager(locale);
            errorMessage = new StringBuilder();
            
            betDateTime = LocalDateTime.now();
            betAmount = request.getParameter(PARAM_BET_AMOUNT);
            String eventId = request.getParameter(PARAM_EVENT_ID);
            event = eventService.getEventById(eventId);
        }
        String outcomeType = request.getParameter(PARAM_OUTCOME_TYPE);
        String outcomeCoeffOnPage = request.getParameter(PARAM_OUTCOME_COEFFICIENT);
        
        //TODO можно сделать валидацию через паттерн chainresponsibility?
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (!validatorService.isValidBetAmount(betAmount)) {
            errorMessage.append(MESSAGE_ERROR_BET_AMOUNT).append(MESSAGE_SEPARATOR);
        }
        
        if (!validatorService.isValidOutcomeCoeffOnPage(outcomeCoeffOnPage, event, outcomeType)) {
            errorMessage.append(MESSAGE_ERROR_OUTCOME_COEFF).append(MESSAGE_SEPARATOR);
        }
        
        if (!validatorService.isValidBetTime(betDateTime, event)) {
            errorMessage.append(MESSAGE_ERROR_BET_TIME).append(MESSAGE_SEPARATOR);
        }
        
        return errorMessage.toString().trim();
    }
    
    
}

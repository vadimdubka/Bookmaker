package com.dubatovka.app.controller.command_impl.authorization;


import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ServiceFactory;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.PlayerServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class RegisterCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        
        String errorMessage = validateRequestParams(request);
        
        if (errorMessage.isEmpty()) {
            PlayerService playerService = new PlayerServiceImpl();
            String email = request.getParameter(PARAM_EMAIL);
            String password = request.getParameter(PARAM_PASSWORD);
            String fName = request.getParameter(PARAM_FNAME);
            String mName = request.getParameter(PARAM_MNAME);
            String lName = request.getParameter(PARAM_LNAME);
            String birthDate = request.getParameter(PARAM_BIRTHDATE);
            if (playerService.registerPlayer(email, password, fName, mName, lName, birthDate)) {
                navigator = PageNavigator.REDIRECT_GOTO_INDEX;
            } else {
                navigator = PageNavigator.FORWARD_PAGE_REGISTER;
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage);
            navigator = PageNavigator.FORWARD_PAGE_REGISTER;
        }
        return navigator;
    }
    
    private String validateRequestParams(HttpServletRequest request) {
        ValidatorService validatorService = ServiceFactory.getInstance().getValidatorService();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        String passwordAgain = request.getParameter(PARAM_PASSWORD_AGAIN);
        String fName = request.getParameter(PARAM_FNAME);
        String mName = request.getParameter(PARAM_MNAME);
        String lName = request.getParameter(PARAM_LNAME);
        String birthDate = request.getParameter(PARAM_BIRTHDATE);
        
        if (validatorService.isValidEmail(email)) {
            request.setAttribute(ATTR_EMAIL_INPUT, email);
        } else {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_EMAIL)).append(MESSAGE_SEPARATOR);
        }
        
        if (!validatorService.isValidPassword(password, passwordAgain)) {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_PASSWORD)).append(WHITESPACE)
                    .append(messageManager.getMessage(MESSAGE_PASSWORD_MISMATCH)).append(MESSAGE_SEPARATOR);
        }
        
        if (validatorService.isValidName(fName)) {
            request.setAttribute(ATTR_FNAME_INPUT, fName);
        } else {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_NAME)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidName(mName)) {
            request.setAttribute(ATTR_MNAME_INPUT, mName);
        } else {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_NAME)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidName(lName)) {
            request.setAttribute(ATTR_LNAME_INPUT, lName);
        } else {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_NAME)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidBirthdate(birthDate)) {
            request.setAttribute(ATTR_BIRTHDATE_INPUT, birthDate);
        } else {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_BIRTHDATE)).append(MESSAGE_SEPARATOR);
        }
        
        return errorMessage.toString().trim();
    }
}
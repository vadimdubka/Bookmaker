package com.dubatovka.app.controller.impl.authorization;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class RegisterCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PAGE_REGISTER;
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
        
        validateRequestParams(messageManager, errorMessage, email, password, passwordAgain, fName, mName, lName, birthDate);
        validateCommand(email, password, passwordAgain, fName, mName, lName, birthDate, messageManager, errorMessage, request);
        if (errorMessage.toString().trim().isEmpty()) {
            try (PlayerService playerService = ServiceFactory.getPlayerService()) {
                boolean isRegPlayer = playerService.registerPlayer(email, password, fName, mName, lName, birthDate);
                if (isRegPlayer) {
                    navigator = PageNavigator.REDIRECT_GOTO_INDEX;
                }
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage);
            navigator = PageNavigator.FORWARD_PAGE_REGISTER;
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        return navigator;
    }
    
    private void validateCommand(String email, String password, String passwordAgain, String fName, String mName, String lName, String birthDate, MessageManager messageManager, StringBuilder errorMessage, ServletRequest request) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (validatorService.isValidEmail(email)) {
            request.setAttribute(ATTR_EMAIL_INPUT, email);
        } else {
            errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_EMAIL)).append(MESSAGE_SEPARATOR);
        }
        if (!validatorService.isValidPassword(password, passwordAgain)) {
            errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_PASSWORD)).append(WHITESPACE)
                    .append(messageManager.getMessageByKey(MESSAGE_ERR_PASSWORD_MISMATCH)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidName(fName)) {
            request.setAttribute(ATTR_FNAME_INPUT, fName);
        } else {
            errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_NAME)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidName(mName)) {
            request.setAttribute(ATTR_MNAME_INPUT, mName);
        } else {
            errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_NAME)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidName(lName)) {
            request.setAttribute(ATTR_LNAME_INPUT, lName);
        } else {
            errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_NAME)).append(MESSAGE_SEPARATOR);
        }
        if (validatorService.isValidBirthdate(birthDate)) {
            request.setAttribute(ATTR_BIRTHDATE_INPUT, birthDate);
        } else {
            errorMessage.append(messageManager.getMessageByKey(MESSAGE_ERR_INVALID_BIRTHDATE)).append(MESSAGE_SEPARATOR);
        }
    }
}
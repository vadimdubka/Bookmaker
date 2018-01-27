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

import static com.dubatovka.app.manager.ConfigConstant.ATTR_BIRTHDATE_INPUT;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_EMAIL_INPUT;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_FNAME_INPUT;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_LNAME_INPUT;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_MNAME_INPUT;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_BIRTHDATE;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_EMAIL;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_NAME;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_INVALID_PASSWORD;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERR_PASSWORD_MISMATCH;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_BIRTHDATE;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_EMAIL;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_FNAME;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_LNAME;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_MNAME;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PASSWORD;
import static com.dubatovka.app.manager.ConfigConstant.PARAM_PASSWORD_AGAIN;

public class RegisterCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PAGE_REGISTER;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        String passwordAgain = request.getParameter(PARAM_PASSWORD_AGAIN);
        String fName = request.getParameter(PARAM_FNAME);
        String mName = request.getParameter(PARAM_MNAME);
        String lName = request.getParameter(PARAM_LNAME);
        String birthDate = request.getParameter(PARAM_BIRTHDATE);
        
        validateRequestParams(messageManager, email, password, passwordAgain, fName, mName, lName, birthDate);
        validateCommand(email, password, passwordAgain, fName, mName, lName, birthDate, messageManager, request);
        if (messageManager.isErrMessEmpty()) {
            try (PlayerService playerService = ServiceFactory.getPlayerService()) {
                boolean isRegPlayer = playerService.registerPlayer(email, password, fName, mName, lName, birthDate);
                if (isRegPlayer) {
                    navigator = PageNavigator.REDIRECT_GOTO_INDEX;
                }
            }
        }
        setMessagesToRequest(messageManager, request);
        return navigator;
    }
    
    private void validateCommand(String email, String password, String passwordAgain, String fName, String mName, String lName, String birthDate, MessageManager messageManager, ServletRequest request) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (validatorService.isValidEmail(email)) {
            request.setAttribute(ATTR_EMAIL_INPUT, email);
        } else {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_EMAIL);
        }
        if (!validatorService.isValidPassword(password, passwordAgain)) {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_PASSWORD);
            messageManager.appendErrMessByKey(MESSAGE_ERR_PASSWORD_MISMATCH);
        }
        if (validatorService.isValidName(fName)) {
            request.setAttribute(ATTR_FNAME_INPUT, fName);
        } else {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_NAME);
        }
        if (validatorService.isValidName(mName)) {
            request.setAttribute(ATTR_MNAME_INPUT, mName);
        } else {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_NAME);
        }
        if (validatorService.isValidName(lName)) {
            request.setAttribute(ATTR_LNAME_INPUT, lName);
        } else {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_NAME);
        }
        if (validatorService.isValidBirthdate(birthDate)) {
            request.setAttribute(ATTR_BIRTHDATE_INPUT, birthDate);
        } else {
            messageManager.appendErrMessByKey(MESSAGE_ERR_INVALID_BIRTHDATE);
        }
    }
}
package com.dubatovka.app.controller.command.impl.authorization;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.entity.Admin;
import com.dubatovka.app.entity.Analyst;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.UserService;
import com.dubatovka.app.service.ValidatorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class LoginCommand implements Command {
    private HttpSession session;
    private MessageManager messageManager;
    private StringBuilder errorMessage;
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        session = request.getSession();
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        messageManager = MessageManager.getMessageManager(locale);
        errorMessage = new StringBuilder();
        
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        
        boolean isValid = isValidData(email, password);
        if (isValid) {
            UserService userService = new UserService();
            User user = userService.authorizeUser(email, password);
            if (user != null) {
                setUserToSession(user);
            } else {
                errorMessage.append(messageManager.getMessage(MESSAGE_LOGIN_MISMATCH)).append(MESSAGE_SEPARATOR);
                request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage.toString().trim());
                request.setAttribute(ATTR_EMAIL_INPUT, email);
            }
        } else {
            request.setAttribute(ATTR_EMAIL_INPUT, email);
            request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage.toString().trim());
        }
        PageNavigator navigator = PageNavigator.FORWARD_PAGE_INDEX;
        return navigator;
    }
    
    private boolean isValidData(String email, String password) {
        boolean valid = true;
        
        if (!ValidatorService.validateEmail(email)) {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_EMAIL)).append(MESSAGE_SEPARATOR);
            valid = false;
        }
        if (!ValidatorService.validatePassword(password)) {
            errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_PASSWORD)).append(MESSAGE_SEPARATOR);
            valid = false;
        }
        return valid;
    }
    
    private void setUserToSession(User user) {
        session.setAttribute(ATTR_USER, user);
        session.setAttribute(ATTR_ROLE, user.getRole());
        Class userClass = user.getClass();
        if (userClass == Player.class) {
            Player player = (Player) user;
            session.setAttribute(ATTR_PLAYER, player);
        } else if (userClass == Admin.class) {
            Admin admin = (Admin) user;
            session.setAttribute(ATTR_ADMIN, admin);
        } else {
            Analyst analyst = (Analyst) user;
            session.setAttribute(ATTR_ANALYST, analyst);
        }
    }
}

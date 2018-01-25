package com.dubatovka.app.controller.impl.authorization;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Admin;
import com.dubatovka.app.entity.Analyst;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.UserService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class LoginCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PAGE_INDEX;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        
        validateRequestParams(errorMessage, email, password);
        validateCommand(email, password, errorMessage, messageManager);
        if (errorMessage.toString().trim().isEmpty()) {
            try (UserService userService = ServiceFactory.getUserService()) {
                User user = userService.authorizeUser(email, password);
                if (user != null) {
                    setUserToSession(user, session);
                } else {
                    errorMessage.append(messageManager.getMessage(MESSAGE_LOGIN_MISMATCH)).append(MESSAGE_SEPARATOR);
                }
            }
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        return navigator;
    }
    
    private void validateCommand(String email, String password, StringBuilder errorMessage, MessageManager messageManager) {
        if (errorMessage.toString().trim().isEmpty()) {
            ValidatorService validatorService = ServiceFactory.getValidatorService();
            
            if (!validatorService.isValidEmail(email)) {
                errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_EMAIL)).append(MESSAGE_SEPARATOR);
            }
            if (!validatorService.isValidPassword(password)) {
                errorMessage.append(messageManager.getMessage(MESSAGE_INVALID_PASSWORD)).append(MESSAGE_SEPARATOR);
            }
        }
    }
    
    private void setUserToSession(User user, HttpSession session) {
        session.setAttribute(ATTR_USER, user);
        session.setAttribute(ATTR_ROLE, user.getRole());
        Class userClass = user.getClass();
        if (userClass == Player.class) {
            Player player = (Player) user;
            try (PlayerService playerService = ServiceFactory.getPlayerService()) {
                playerService.updatePlayerInfo(player);
            }
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

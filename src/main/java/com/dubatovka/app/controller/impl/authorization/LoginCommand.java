package com.dubatovka.app.controller.impl.authorization;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Admin;
import com.dubatovka.app.entity.Analyst;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.service.MessageService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.UserService;
import com.dubatovka.app.service.ValidationService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dubatovka.app.config.ConfigConstant.ATTR_ADMIN;
import static com.dubatovka.app.config.ConfigConstant.ATTR_ANALYST;
import static com.dubatovka.app.config.ConfigConstant.ATTR_PLAYER;
import static com.dubatovka.app.config.ConfigConstant.ATTR_ROLE;
import static com.dubatovka.app.config.ConfigConstant.ATTR_USER;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_EMAIL;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_INVALID_PASSWORD;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_ERR_LOGIN_MISMATCH;
import static com.dubatovka.app.config.ConfigConstant.PARAM_EMAIL;
import static com.dubatovka.app.config.ConfigConstant.PARAM_PASSWORD;

public class LoginCommand implements Command {
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PAGE_INDEX;
        HttpSession session = request.getSession();
        MessageService messageService = ServiceFactory.getMessageService(session);
        
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        
        validateRequestParams(messageService, email, password);
        validateCommand(email, password, messageService);
        if (messageService.isErrMessEmpty()) {
            try (UserService userService = ServiceFactory.getUserService()) {
                User user = userService.authorizeUser(email, password);
                if (user != null) {
                    setUserToSession(user, session);
                } else {
                    messageService.appendErrMessByKey(MESSAGE_ERR_LOGIN_MISMATCH);
                }
            }
        }
        setMessagesToRequest(messageService, request);
        return navigator;
    }
    
    private void validateCommand(String email, String password, MessageService messageService) {
        if (messageService.isErrMessEmpty()) {
            ValidationService validationService = ServiceFactory.getValidationService();
            if (!validationService.isValidEmail(email)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_EMAIL);
            }
            if (!validationService.isValidPassword(password)) {
                messageService.appendErrMessByKey(MESSAGE_ERR_INVALID_PASSWORD);
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

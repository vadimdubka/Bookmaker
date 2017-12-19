package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.UserDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Admin;
import com.dubatovka.app.entity.Analyst;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl extends AbstractService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO = daoFactory.getUserDAO();
    
    /**
     * Provides authorisation operation for user. Calls DAO layer to init {@link User} object due to given parameters.
     */
    public User authorizeUser(String email, String password) {
        email = email.toLowerCase().trim();
        password = Encryptor.encryptMD5(password);
        User user = null;
        try {
            user = userDAO.authorizeUser(email, password);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        if (user != null) {
            User.UserRole role = user.getRole();
            if (role == User.UserRole.PLAYER) {
                user = new Player(user);
            } else if (role == User.UserRole.ADMIN) {
                user = new Admin(user);
            } else {
                user = new Analyst(user);
            }
        }
        return user;
    }
}
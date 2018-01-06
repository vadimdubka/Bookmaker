package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.PlayerDAO;
import com.dubatovka.app.dao.UserDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.manager.Encryptor;
import com.dubatovka.app.service.PlayerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The type Player service.
 */
public class PlayerServiceImpl extends AbstractService implements PlayerService {
    private static final Logger logger = LogManager.getLogger(PlayerServiceImpl.class);
    private final UserDAO userDAO = daoFactory.getUserDAO();
    private final PlayerDAO playerDAO = daoFactory.getPlayerDAO();
    
    @Override
    public List<Player> getAllPlayers() {
        List<Player> playerList = null;
        try {
            playerList = playerDAO.readAllPlayers();
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        
        return playerList;
    }
    
    @Override
    public boolean registerPlayer(String email, String password, String fName, String mName, String lName, String birthDate) {
        email = email.trim().toLowerCase();
        password = Encryptor.encryptMD5(password);
        if (fName != null) {
            fName = fName.trim().toUpperCase();
        }
        if (mName != null) {
            mName = mName.trim().toUpperCase();
        }
        if (lName != null) {
            lName = lName.trim().toUpperCase();
        }
        boolean result = false;
        try {
            int id = userDAO.insertUser(email, password);
            int insertedRows = playerDAO.insertPlayer(id, fName, mName, lName, birthDate);
            if ((id != 0) && (insertedRows == 1)) {
                result = true;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return result;
    }
    
    
    @Override
    public void updatePlayerInfo(Player player) {
        int id = player.getId();
        try {
            Player playerWithLastInfo = playerDAO.readPlayerById(id);
            player.setProfile(playerWithLastInfo.getProfile());
            player.setAccount(playerWithLastInfo.getAccount());
            player.setVerification(playerWithLastInfo.getVerification());
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
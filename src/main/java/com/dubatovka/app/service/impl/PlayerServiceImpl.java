package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.PlayerDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.PlayerProfile;
import com.dubatovka.app.manager.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PlayerServiceImpl extends AbstractService {
    private static final Logger logger = LogManager.getLogger(PlayerServiceImpl.class);
    private final PlayerDAO playerDAO = daoFactory.getPlayerDAO();
    
    public List<Player> getAllPlayers() {
        List<Player> playerList = null;
        try {
            playerList = playerDAO.readPlayers();
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        
        return playerList;
    }
    
    //TODO разбить на 2 метда, который проверяет, есть ли игрок или нет
    public boolean registerPlayer(String email, String password, String fName, String mName, String lName) {
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
        try {
            int id = playerDAO.insertUserPlayer(email, password);
            if ((id != 0) && playerDAO.insertPlayer(id, fName, mName, lName)) {
                return true;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return false;
    }
    
    boolean initPlayerInfo(Player player) {
        return updateProfileInfo(player);
    }
    
    //TODO разбить на 2 метда, который проверяет, есть ли игрок или нет
    public boolean updateProfileInfo(Player player) {
        int id = player.getId();
        try {
            PlayerProfile profile = playerDAO.takeProfile(player.getId());
            String email = playerDAO.defineEmailById(id);
            player.setProfile(profile);
            player.setEmail(email);
            return true;
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return false;
    }
}
package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.PlayerDAO;
import com.dubatovka.app.dao.TransactionDAO;
import com.dubatovka.app.dao.UserDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.Transaction;
import com.dubatovka.app.manager.Encryptor;
import com.dubatovka.app.service.PlayerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


public class PlayerServiceImpl extends PlayerService {
    private static final Logger logger = LogManager.getLogger(PlayerServiceImpl.class);
    private final UserDAO userDAO = daoHelper.getUserDAO();
    private final PlayerDAO playerDAO = daoHelper.getPlayerDAO();
    private final TransactionDAO transactionDAO = daoHelper.getTransactionDAO();
    private final BetDAO betDAO = daoHelper.getBetDAO();
    
    PlayerServiceImpl() {
    }
    
    PlayerServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
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
    
    @Override
    public void makeBet(int playerId, int eventId, String outcomeType, BigDecimal coefficient, BigDecimal betAmount, Transaction.TransactionType transactionType, StringBuilder errorMessage) {
        try {
            //TODO проверить тразакцию на ACID, на согласованность
            daoHelper.beginTransaction();
            betDAO.insertBet(playerId, eventId, outcomeType, coefficient, betAmount);
            playerDAO.changeBalance(playerId, betAmount, transactionType);
            daoHelper.commit();
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append("Database connection error while doing sql transaction.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Database connection error while doing sql transaction. " + e);
            errorMessage.append("Database connection error while doing sql transaction.");
        }
    }
    
    /**
     * Calls DAO layer to make an account transaction of definite
     * {@link Transaction.TransactionType}.
     *
     * @param player          player who processes transaction
     * @param amount          amount of money player transacts
     * @param transactionType type of transaction
     * @return true if transaction proceeded successfully
     * @see TransactionDAO#insertTransaction(int, BigDecimal, Transaction.TransactionType)
     * @see PlayerDAO#changeBalance(int, BigDecimal, Transaction.TransactionType)
     */
    @Override
    public int makeTransaction(Player player, BigDecimal amount, Transaction.TransactionType transactionType) {
        int playerId = player.getId();
        int result = 0;
        try {
            daoHelper.beginTransaction();
            int insTransactId = transactionDAO.insertTransaction(playerId, amount, transactionType);
            boolean isChanged = playerDAO.changeBalance(playerId, amount, transactionType);
            if ((insTransactId != 0) && isChanged) {
                daoHelper.commit();
                result = insTransactId;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Database connection error while doing sql transaction. " + e);
        }
        return result;
    }
}
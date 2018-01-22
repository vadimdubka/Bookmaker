package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.PlayerDAO;
import com.dubatovka.app.dao.TransactionDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Transaction;
import com.dubatovka.app.service.BetService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERROR_SQL_OPERATION;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERROR_SQL_TRANSACTION;

public class BetServiceImpl extends BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);
    private final BetDAO betDAO = daoHelper.getBetDAO();
    private final PlayerDAO playerDAO = daoHelper.getPlayerDAO();
    private final TransactionDAO transactionDAO = daoHelper.getTransactionDAO();
    
    BetServiceImpl() {
    }
    
    BetServiceImpl(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    @Override
    public List<Bet> getBetListForPlayer(int playerId) {
        List<Bet> betList = null;
        try {
            betList = betDAO.readBetListForPlayer(playerId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return betList;
    }
    
    @Override
    public Set<Bet> getBetSetForEventAndStatus(int eventId, Bet.Status status) {
        Set<Bet> betList = null;
        try {
            betList = betDAO.readBetSetForEventAndStatus(eventId, status);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return betList;
    }
    
    @Override
    public Map<String, Map<String, String>> getWinBetInfo(int categoryId) {
        Map<String, Map<String, String>> winBetInfoMap = null;
        try {
            winBetInfoMap = betDAO.readWinBetInfoMap(categoryId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return winBetInfoMap;
    }
    
    @Override
    public void payWinBet(int eventId, StringBuilder errorMessage) {
        Set<Bet> winBetSet = getBetSetForEventAndStatus(eventId, Bet.Status.WIN);
        if ((winBetSet != null) && !winBetSet.isEmpty()) {
            try {
                daoHelper.beginTransaction();
                boolean isTransactionOk = true;
                for (Bet bet : winBetSet) {
                    int playerId = bet.getPlayerId();
                    BigDecimal amount = bet.getAmount();
                    BigDecimal coefficient = bet.getCoefficient();
                    BigDecimal winning = amount.multiply(coefficient);
                    Transaction.TransactionType transactionType = Transaction.TransactionType.REPLENISH;
                    int transactId = transactionDAO.insertTransaction(playerId, winning, transactionType);
                    boolean isBalanceUpd = playerDAO.updateBalance(playerId, winning, transactionType);
                    if (!isBalanceUpd || (transactId == 0)) {
                        isTransactionOk = false;
                    }
                }
                int betUpdCount = betDAO.updateBetStatus(eventId, Bet.Status.WIN, Bet.Status.PAID);
                if ((betUpdCount == winBetSet.size()) && isTransactionOk) {
                    daoHelper.commit();
                } else {
                    daoHelper.rollback();
                }
            } catch (DAOException e) {
                logger.log(Level.ERROR, e.getMessage());
                errorMessage.append(MESSAGE_ERROR_SQL_OPERATION);
            } catch (SQLException e) {
                logger.log(Level.ERROR, MESSAGE_ERROR_SQL_TRANSACTION + e);
                errorMessage.append(MESSAGE_ERROR_SQL_TRANSACTION);
            }
        }
    }
    
    @Override
    public void makeBet(Bet bet, StringBuilder errorMessage) {
        try {
            daoHelper.beginTransaction();
            boolean isBetIns = betDAO.insertBet(bet);
            boolean isBalUpd = playerDAO.updateBalance(bet.getPlayerId(), bet.getAmount(), Transaction.TransactionType.WITHDRAW);
            if (isBetIns && isBalUpd) {
                daoHelper.commit();
            } else {
                daoHelper.rollback();
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
            errorMessage.append(MESSAGE_ERROR_SQL_OPERATION);
        } catch (SQLException e) {
            logger.log(Level.ERROR, MESSAGE_ERROR_SQL_TRANSACTION + e);
            errorMessage.append(MESSAGE_ERROR_SQL_TRANSACTION);
        }
    }
    
}

package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOHelper;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public abstract class PlayerService extends AbstractService {
    protected PlayerService() {
    }
    
    protected PlayerService(DAOHelper daoHelper) {
        super(daoHelper);
    }
    
    public abstract List<Player> getAllPlayers();
    
    //TODO уточнить как именовать метод, если основное значение это зарегитрировать пользователя, а не посмотретть, зарегистрирован ли он
    public abstract boolean registerPlayer(String email, String password, String fName, String mName, String lName, String birthDate);
    
    public abstract void updatePlayerInfo(Player player);
    
    public abstract void makeBet(int playerId, int eventId, String outcomeType, BigDecimal coefficient, BigDecimal betAmount, Transaction.TransactionType transactionType, StringBuilder errorMessage);
    
    public abstract int makeTransaction(Player player, BigDecimal amount, Transaction.TransactionType transactionType);
}

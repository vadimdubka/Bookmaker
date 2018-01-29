package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public abstract class TransactionService extends DAOProviderHolder {
    protected TransactionService() {
    }
    
    protected TransactionService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    public abstract List<Transaction> takePlayerTransactions(int id, String month);
    
    public abstract List<Transaction> takeTransactionList(String filterByType, String month, boolean isSortByAmount);
    
    public abstract BigDecimal defineMaxPayment(List<Transaction> transactions);
    
    public abstract BigDecimal countTotalPayment(List<Transaction> transactions);
    
    public abstract BigDecimal defineMaxWithdrawal(List<Transaction> transactions);
    
    public abstract BigDecimal countTotalWithdrawal(List<Transaction> transactions);
    
    public abstract int makeTransaction(int playerId, BigDecimal amount, Transaction.TransactionType transactionType);
}

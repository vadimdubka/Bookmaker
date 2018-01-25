package com.dubatovka.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    /**
     * Transaction unique id.
     */
    private int id;
    /**
     * Player id who made this transaction.
     */
    private int playerId;
    /**
     * Transaction date.
     */
    private LocalDateTime date;
    /**
     * Transaction amount.
     */
    private BigDecimal amount;
    
    private TransactionType type;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        
        Transaction that = (Transaction) o;
        
        if (id != that.id) return false;
        if (playerId != that.playerId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return type == that.type;
    
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + playerId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
    
    public enum TransactionType {
        REPLENISH, WITHDRAW
    }
}

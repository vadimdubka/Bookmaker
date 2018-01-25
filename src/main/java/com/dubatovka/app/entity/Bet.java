package com.dubatovka.app.entity;

import com.dubatovka.app.manager.ConfigConstant;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bet {
    private int playerId;
    private int eventId;
    private String outcomeType;
    private LocalDateTime date;
    private BigDecimal coefficient;
    private BigDecimal amount;
    private Status status;
    
    public Bet() {
    }
    
    public Bet(int playerId, int eventId, String outcomeType, LocalDateTime date, BigDecimal coefficient, BigDecimal amount, Status status) {
        this.playerId = playerId;
        this.eventId = eventId;
        this.outcomeType = outcomeType;
        this.date = date;
        this.coefficient = coefficient;
        this.amount = amount;
        this.status = status;
    }
    
    public int getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public int getEventId() {
        return eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    public String getOutcomeType() {
        return outcomeType;
    }
    
    public void setOutcomeType(String outcomeType) {
        this.outcomeType = outcomeType;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public BigDecimal getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bet)) return false;
        
        Bet bet = (Bet) o;
        
        if (playerId != bet.playerId) return false;
        if (eventId != bet.eventId) return false;
        if (outcomeType != null ? !outcomeType.equals(bet.outcomeType) : bet.outcomeType != null) return false;
        if (date != null ? !date.equals(bet.date) : bet.date != null) return false;
        if (coefficient != null ? !coefficient.equals(bet.coefficient) : bet.coefficient != null) return false;
        if (amount != null ? !amount.equals(bet.amount) : bet.amount != null) return false;
        return status == bet.status;
        
    }
    
    @Override
    public int hashCode() {
        int result = playerId;
        result = 31 * result + eventId;
        result = 31 * result + (outcomeType != null ? outcomeType.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (coefficient != null ? coefficient.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Bet{" +
                "playerId=" + playerId +
                ", eventId=" + eventId +
                ", outcomeType='" + outcomeType + '\'' +
                ", date=" + date +
                ", coefficient=" + coefficient +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
    
    public enum Status {
        NEW(ConfigConstant.NEW),
        LOSING(ConfigConstant.LOSING),
        WIN(ConfigConstant.WIN),
        PAID(ConfigConstant.PAID);
        
        private final String status;
        
        Status(String status) {
            this.status = status;
        }
        
        public String getStatus() {
            return status;
        }
    }
}

package com.dubatovka.app.entity;

import java.math.BigDecimal;

public class PlayerAccount {

    /**
     * Object which contains player status data.
     */
    private PlayerStatus status;
    /**
     * Player balance value.
     */
    private BigDecimal balance;
    /**
     * Player current month withdrawal value.
     */
    private BigDecimal   thisMonthWithdrawal;
    
    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getThisMonthWithdrawal() {
        return thisMonthWithdrawal;
    }

    public void setThisMonthWithdrawal(BigDecimal thisMonthWithdrawal) {
        this.thisMonthWithdrawal = thisMonthWithdrawal;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerAccount)) return false;
        
        PlayerAccount that = (PlayerAccount) o;
        
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        return thisMonthWithdrawal != null ? thisMonthWithdrawal.equals(that.thisMonthWithdrawal) : that.thisMonthWithdrawal == null;
    
    }
    
    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (thisMonthWithdrawal != null ? thisMonthWithdrawal.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "PlayerAccount{" +
                "status=" + status +
                ", balance=" + balance +
                ", thisMonthWithdrawal=" + thisMonthWithdrawal +
                '}';
    }
}
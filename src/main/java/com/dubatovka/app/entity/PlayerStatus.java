package com.dubatovka.app.entity;

import com.dubatovka.app.config.ConfigConstant;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlayerStatus implements Serializable {
    private static final long serialVersionUID = 539544012880393907L;
    /**
     * Player account status value.
     */
    private Status status;
    /**
     * Player limit for 1 bet.
     */
    private BigDecimal betLimit;
    /**
     * Player withdrawal limit for 1 month.
     */
    private BigDecimal withdrawalLimit;
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public BigDecimal getBetLimit() {
        return betLimit;
    }
    
    public void setBetLimit(BigDecimal betLimit) {
        this.betLimit = betLimit;
    }
    
    public BigDecimal getWithdrawalLimit() {
        return withdrawalLimit;
    }
    
    public void setWithdrawalLimit(BigDecimal withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerStatus)) return false;
        
        PlayerStatus that = (PlayerStatus) o;
        
        if (status != that.status) return false;
        if (betLimit != null ? !betLimit.equals(that.betLimit) : that.betLimit != null) return false;
        return withdrawalLimit != null ? withdrawalLimit.equals(that.withdrawalLimit) : that.withdrawalLimit == null;
        
    }
    
    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (betLimit != null ? betLimit.hashCode() : 0);
        result = 31 * result + (withdrawalLimit != null ? withdrawalLimit.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "PlayerStatus{" +
                "status=" + status +
                ", betLimit=" + betLimit +
                ", withdrawalLimit=" + withdrawalLimit +
                '}';
    }
    
    public enum Status {
        UNVERIFIED(ConfigConstant.UNVERIFIED), BASIC(ConfigConstant.BASIC), VIP(ConfigConstant.VIP), BAN(ConfigConstant.BAN);
        
        private final String status;
        
        Status(String status) {
            this.status = status;
        }
        
        public String getStatus() {
            return status;
        }
    }
}

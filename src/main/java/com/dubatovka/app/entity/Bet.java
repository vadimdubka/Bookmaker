package com.dubatovka.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bet {
    private int playerId;
    private Outcome outcome;
    private LocalDateTime date;
    private BigDecimal amount;
    private Status status;
    
    public enum Status {
        NEW(),
        LOOSING(),
        WIN(),
        PAID();
    }
}

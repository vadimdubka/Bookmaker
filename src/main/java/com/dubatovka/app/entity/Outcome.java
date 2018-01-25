package com.dubatovka.app.entity;

import com.dubatovka.app.manager.ConfigConstant;

import java.math.BigDecimal;

public class Outcome {
    private int eventId;
    private Type type;
    private BigDecimal coefficient;
    
    public Outcome() {
    }
    
    public Outcome(int eventId, BigDecimal coefficient, Type type) {
        this.eventId = eventId;
        this.coefficient = coefficient;
        this.type = type;
    }
    
    public int getEventId() {
        return eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public BigDecimal getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Outcome)) return false;
        
        Outcome outcome = (Outcome) o;
        
        if (eventId != outcome.eventId) return false;
        if (type != outcome.type) return false;
        return coefficient != null ? coefficient.equals(outcome.coefficient) : outcome.coefficient == null;
        
    }
    
    @Override
    public int hashCode() {
        int result = eventId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (coefficient != null ? coefficient.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Outcome{" +
                "eventId=" + eventId +
                ", type=" + type +
                ", coefficient=" + coefficient +
                '}';
    }
    
    public enum Type {
        TYPE_1(ConfigConstant.TYPE_1), TYPE_X(ConfigConstant.TYPE_X), TYPE_2(ConfigConstant.TYPE_2);
        
        private final String type;
        
        Type(String type) {
            this.type = type;
        }
        
        public String getType() {
            return type;
        }
    }
}

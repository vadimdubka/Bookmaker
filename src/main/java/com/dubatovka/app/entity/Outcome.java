package com.dubatovka.app.entity;

import java.math.BigDecimal;

public class Outcome {
    private int eventId;
    private String type;
    private BigDecimal coefficient;
    
    public int getEventId() {
        return eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
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
        if (type != null ? !type.equals(outcome.type) : outcome.type != null) return false;
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
                ", type='" + type + '\'' +
                ", coefficient=" + coefficient +
                '}';
    }
}

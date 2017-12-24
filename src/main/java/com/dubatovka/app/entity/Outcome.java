package com.dubatovka.app.entity;

public class Outcome {
    private int eventId;
    private String type;
    private double coefficient;
    
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
    
    public double getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Outcome)) return false;
        
        Outcome outcome = (Outcome) o;
        
        if (eventId != outcome.eventId) return false;
        if (Double.compare(outcome.coefficient, coefficient) != 0) return false;
        return type != null ? type.equals(outcome.type) : outcome.type == null;
        
    }
    
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = eventId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(coefficient);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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

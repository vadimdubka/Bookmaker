package com.dubatovka.app.entity;

import java.time.LocalDate;

public class Event {
    private int id;
    private int categoryId;
    private LocalDate date;
    private String participant1;
    private String participant2;
    private int result1;
    private int result2;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getParticipant1() {
        return participant1;
    }
    
    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }
    
    public String getParticipant2() {
        return participant2;
    }
    
    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }
    
    public int getResult1() {
        return result1;
    }
    
    public void setResult1(int result1) {
        this.result1 = result1;
    }
    
    public int getResult2() {
        return result2;
    }
    
    public void setResult2(int result2) {
        this.result2 = result2;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        
        Event event = (Event) o;
        
        if (id != event.id) return false;
        if (categoryId != event.categoryId) return false;
        if (result1 != event.result1) return false;
        if (result2 != event.result2) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        if (participant1 != null ? !participant1.equals(event.participant1) : event.participant1 != null) return false;
        return participant2 != null ? participant2.equals(event.participant2) : event.participant2 == null;
        
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + categoryId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (participant1 != null ? participant1.hashCode() : 0);
        result = 31 * result + (participant2 != null ? participant2.hashCode() : 0);
        result = 31 * result + result1;
        result = 31 * result + result2;
        return result;
    }
    
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", date=" + date +
                ", participant1='" + participant1 + '\'' +
                ", participant2='" + participant2 + '\'' +
                ", result1=" + result1 +
                ", result2=" + result2 +
                '}';
    }
}

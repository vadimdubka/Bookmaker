package com.dubatovka.app.entity;

public class Analyst extends User {
    
    public Analyst() {
    }
    
    public Analyst(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setRole(UserRole.ANALYST);
        setRegistrationDate(user.getRegistrationDate());
    }
    
    
    @Override
    public String toString() {
        return "Analyst{" + super.toString() + '}';
    }
    
}
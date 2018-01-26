package com.dubatovka.app.entity;

import java.io.Serializable;

public class Analyst extends User implements Serializable {
    private static final long serialVersionUID = -5826755955233635473L;
    
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
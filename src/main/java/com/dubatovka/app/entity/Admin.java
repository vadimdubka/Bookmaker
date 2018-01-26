package com.dubatovka.app.entity;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = -8023371264325607651L;
    
    public Admin() {
    }
    
    public Admin(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setRole(UserRole.ADMIN);
        setRegistrationDate(user.getRegistrationDate());
    }
    
    @Override
    public String toString() {
        return "Admin{" + super.toString() + '}';
    }
    
}
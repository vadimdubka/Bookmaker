package com.dubatovka.app.entity;

public class Admin extends User {
    
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
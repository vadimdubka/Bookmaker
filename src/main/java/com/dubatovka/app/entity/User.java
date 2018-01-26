package com.dubatovka.app.entity;

import com.dubatovka.app.manager.ConfigConstant;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 10220719251876555L;
    private int id;
    private String email;
    private UserRole role;
    private LocalDate registrationDate;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                       Objects.equals(email, user.email) &&
                       role == user.role &&
                       Objects.equals(registrationDate, user.registrationDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, email, role, registrationDate);
    }
    
    
    @Override
    public String toString() {
        return "User{" + "id=" + id +
                       ", email='" + email + '\'' +
                       ", role=" + role +
                       ", registrationDate=" + registrationDate +
                       '}';
    }
    
    public enum UserRole {
        GUEST(ConfigConstant.GUEST),
        PLAYER(ConfigConstant.PLAYER),
        ADMIN(ConfigConstant.ADMIN),
        ANALYST(ConfigConstant.ANALYST);
        
        private final String role;
        
        UserRole(String role) {
            this.role = role;
        }
        
        public String getRole() {
            return role;
        }
    }
    
}

package com.dubatovka.app.service;

public interface ValidatorService {
    boolean validateEmail(String email);
    
    boolean validatePassword(String password);
    
    boolean validatePassword(String password, String passwordAgain);
    
    boolean validateName(String name);
    
    boolean matchPattern(String string, String regex);
}

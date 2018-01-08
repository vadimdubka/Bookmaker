package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;

import java.time.LocalDateTime;

public abstract class ValidatorService  {
    public abstract boolean isValidEmail(String email);
    
    public abstract boolean isValidPassword(String password);
    
    public abstract boolean isValidPassword(String password, String passwordAgain);
    
    public abstract boolean isValidName(String name);
    
    public abstract boolean isMatchPattern(String string, String regex);
    
    public abstract boolean isValidBirthdate(String birthDate);
    
    public abstract boolean isValidBetAmount(String betAmount);
    
    public abstract boolean isValidOutcomeCoeffOnPage(String outcomeCoeffOnPage, Event event, String outcomeType);
    
    public abstract boolean isValidBetTime(LocalDateTime betDateTime, Event event);
}

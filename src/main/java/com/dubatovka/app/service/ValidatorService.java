package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;

import java.time.LocalDateTime;

public interface ValidatorService {
    boolean isValidEmail(String email);
    
    boolean isValidPassword(String password);
    
    boolean isValidPassword(String password, String passwordAgain);
    
    boolean isValidName(String name);
    
    boolean isMatchPattern(String s, String regex);
    
    boolean isValidBirthdate(String birthDate);
    
    boolean isValidBetAmount(String betAmount);
    
    boolean isValidOutcomeCoeffOnPage(String outcomeCoeffOnPage, Event event, String outcomeType);
    
    boolean isValidBetTime(LocalDateTime betDateTime, Event event);
}

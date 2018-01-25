package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;

import java.time.LocalDateTime;

public interface ValidatorService {
    boolean isValidEmail(String email);
    
    boolean isValidPassword(String password);
    
    boolean isValidPassword(String password, String passwordAgain);
    
    boolean isValidName(String name);
    
    boolean isValidBirthdate(String birthDate);
    
    boolean isValidBetAmount(String betAmount);
    
    boolean isValidId(String id);
    
    boolean isNotNull(Object object);
    
    boolean isValidOutcomeCoeffOnPage(String outcomeCoeffOnPage, Event event, String outcomeType);
    
    boolean isValidBetTime(LocalDateTime betDateTime, LocalDateTime eventDateTime);
    
    boolean isValidRequestParam(String... params);
    
    boolean isValidEventDateTime(String dateTimeStr);
    
    boolean isValidEventParticipantName(String participant);
    
    boolean isValidEventResult(String eventResult);
    
    boolean isValidOutcomeCoeff(String outcomeCoeff);
}

package com.dubatovka.app.service.impl;

import com.dubatovka.app.service.ValidatorService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO валидатор может делать синглтоном или методы статик?
public class ValidatorServiceImpl implements ValidatorService {
    
    private static final int MAX_EMAIL_LENGTH = 320;
    private static final int MAX_EMAIL_NAME_LENGTH = 64;
    private static final int MAX_EMAIL_DOMAIN_LENGTH = 255;
    private static final String EMAIL_SPLITERATOR = "@";
    private static final int EMAIL_PAIR_LENGTH = 2;
    
    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
            "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\\w_-]{8,}$";
    private static final String NAME_REGEX = "[A-Za-z]{1,70}";
    
    @Override
    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()
                || email.length() > MAX_EMAIL_LENGTH
                || !matchPattern(email, EMAIL_REGEX)) {
            return false;
        }
        String[] emailPair = email.split(EMAIL_SPLITERATOR);
        if (emailPair.length != EMAIL_PAIR_LENGTH) {
            return false;
        }
        String name = emailPair[0];
        String domain = emailPair[1];
        return name.length() <= MAX_EMAIL_NAME_LENGTH
                && domain.length() <= MAX_EMAIL_DOMAIN_LENGTH;
    }
    
    @Override
    public boolean validatePassword(String password) {
        return password != null && !password.trim().isEmpty() && matchPattern(password, PASSWORD_REGEX);
    }
    
    @Override
    public boolean validatePassword(String password, String passwordAgain) {
        return !(password == null || password.trim().isEmpty() || !password.equals(passwordAgain))
                && validatePassword(password);
    }
    
    @Override
    public boolean validateName(String name) {
        return name == null || name.trim().isEmpty() || matchPattern(name, NAME_REGEX);
    }
    
    public boolean validateBirthdate(String birthdate) {
        if (birthdate == null || birthdate.trim().isEmpty()) {
            return false;
        }
        
        LocalDate date;
        try {
            date = LocalDate.parse(birthdate);
        } catch (DateTimeParseException e) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return date.plusYears(18).isBefore(now) || date.plusYears(18).isEqual(now);
    }
    
    @Override
    public boolean matchPattern(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
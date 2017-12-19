package com.dubatovka.app.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorServiceImpl {
    
    private static final int MAX_EMAIL_LENGTH = 320;
    private static final int MAX_EMAIL_NAME_LENGTH = 64;
    private static final int MAX_EMAIL_DOMAIN_LENGTH = 255;
    private static final String EMAIL_SPLITERATOR = "@";
    private static final int EMAIL_PAIR_LENGTH = 2;
    
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
            "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\\w_-]{8,}$";
    private static final String NAME_REGEX = "[A-Za-z ]{1,70}";
    
    private ValidatorServiceImpl() {
    }
    
    public static boolean validateEmail(String email) {
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
    
    public static boolean validatePassword(String password) {
        return password != null && !password.trim().isEmpty() && matchPattern(password, PASSWORD_REGEX);
    }
    
    public static boolean validatePassword(String password, String passwordAgain) {
        return !(password == null || password.trim().isEmpty() || !password.equals(passwordAgain))
                && validatePassword(password);
    }
    
    public static boolean validateName(String name) {
        return name == null || name.trim().isEmpty() || matchPattern(name, NAME_REGEX);
    }
    
    private static boolean matchPattern(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
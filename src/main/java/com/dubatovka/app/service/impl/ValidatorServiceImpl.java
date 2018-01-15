package com.dubatovka.app.service.impl;

import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.service.ValidatorService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorServiceImpl implements ValidatorService {
    
    private static final int MAX_EMAIL_LENGTH = 320;
    private static final int MAX_EMAIL_NAME_LENGTH = 64;
    private static final int MAX_EMAIL_DOMAIN_LENGTH = 255;
    private static final String EMAIL_SPLITERATOR = "@";
    private static final int EMAIL_PAIR_LENGTH = 2;
    public static final int MIN_PLAYER_AGE = 18;
    
    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
                    "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\\w_-]{8,}$";
    private static final String NAME_REGEX = "[A-Za-z]{1,70}";
    private static final String BET_AMOUNT_REGEX = "^[0-9]{1,3}\\.?[0-9]{0,2}$";
    private static final String ID_REGEX = "[0-9]+";
    private static final String PARTICIPANT_REGEX = "^([a-zA-Z_0-9а-яА-Я]+).{0,100}";
    
    ValidatorServiceImpl() {
    }
    
    @Override
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()
                || email.length() > MAX_EMAIL_LENGTH
                || !isMatchPattern(email, EMAIL_REGEX)) {
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
    public boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty() && isMatchPattern(password, PASSWORD_REGEX);
    }
    
    @Override
    public boolean isValidPassword(String password, String passwordAgain) {
        return !(password == null || password.trim().isEmpty() || !password.equals(passwordAgain))
                && isValidPassword(password);
    }
    
    @Override
    public boolean isValidName(String name) {
        return name == null || name.trim().isEmpty() || isMatchPattern(name, NAME_REGEX);
    }
    
    @Override
    public boolean isValidBirthdate(String birthDate) {
        boolean result = false;
        if ((birthDate != null) && !birthDate.trim().isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(birthDate);
                LocalDate now = LocalDate.now();
                result = date.plusYears(MIN_PLAYER_AGE).isBefore(now) || date.plusYears(MIN_PLAYER_AGE).isEqual(now);
            } catch (DateTimeParseException e) {
                result = false;
            }
        }
        return result;
    }
    
    @Override
    public boolean isValidBetAmount(String betAmount) {
        return (betAmount != null) && isMatchPattern(betAmount, BET_AMOUNT_REGEX);
    }
    
    @Override
    public boolean isValidId(String id) {
        return (id != null) && isMatchPattern(id, ID_REGEX) && (Integer.parseInt(id) > 0);
    }
    
    @Override
    public boolean isNotNull(Object object) {
        return object != null;
    }
    
    @Override
    public boolean isValidOutcomeCoeffOnPage(String outcomeCoeffOnPage, Event event, String outcomeType) {
        Outcome outcome = event.getOutcomeByType(outcomeType);
        BigDecimal coeffOnPage = new BigDecimal(outcomeCoeffOnPage);
        BigDecimal coeffFromDB = outcome.getCoefficient();
        int compareResult = coeffFromDB.compareTo(coeffOnPage);
        return compareResult == 0;
    }
    
    @Override
    public boolean isValidBetTime(LocalDateTime betDateTime, Event event) {
        LocalDateTime eventDateTime = event.getDate();
        return betDateTime.isBefore(eventDateTime);
    }
    
    @Override
    public boolean isValidRequestParam(String... params) {
        boolean result = true;
        for (String param : params) {
            if ((param == null) || param.isEmpty()) {
                result = false;
            }
        }
        return result;
    }
    
    @Override
    public boolean isValidEventDateTime(String dateTimeStr) {
        boolean result;
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            LocalDateTime now = LocalDateTime.now();
            result = dateTime.isAfter(now);
        } catch (DateTimeParseException e) {
            result = false;
        }
        return result;
    }
    
    @Override
    public boolean isValidEventParticipantName(String participant) {
        return (participant != null) && !participant.trim().isEmpty() && isMatchPattern(participant, PARTICIPANT_REGEX);
    }
    
    private boolean isMatchPattern(String s, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
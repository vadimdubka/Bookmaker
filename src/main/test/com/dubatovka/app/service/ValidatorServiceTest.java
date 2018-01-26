package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Outcome;
import com.dubatovka.app.service.impl.ServiceFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ValidatorServiceTest {
    private static ValidatorService validatorService;
    
    @BeforeClass
    public static void setUp() {
        validatorService = ServiceFactory.getValidatorService();
    }
    
    @Test
    public void isValidEmailTest() {
        String validEmail = "any@valid.by";
        Assert.assertTrue(validatorService.isValidEmail(validEmail));
        String inValidEmail = "anyInvalid@va_12lid.bbby";
        Assert.assertFalse(validatorService.isValidEmail(inValidEmail));
    }
    
    @Test
    public void isValidPasswordTest() {
        String validPassword = "S-a100500";
        Assert.assertTrue(validatorService.isValidPassword(validPassword));
        String inValidPassword = "abc8284771";
        Assert.assertFalse(validatorService.isValidPassword(inValidPassword));
    }
    
    @Test
    public void isValidPasswordMatchTest() {
        String firstPassword = "S-a100500";
        String secondPassword = "S-a100500";
        Assert.assertTrue(validatorService.isValidPassword(firstPassword, secondPassword));
        String secondInvalidPassword = "Sa100500";
        Assert.assertFalse(validatorService.isValidPassword(firstPassword, secondInvalidPassword));
    }
    
    @Test
    public void isValidNameTest() {
        String validName = "AnyName";
        Assert.assertTrue(validatorService.isValidName(validName));
        String invalidName = "любоеИмя";
        Assert.assertFalse(validatorService.isValidName(invalidName));
    }
    
    @Test
    public void isValidBirthdateTest() {
        String validBirthdate = "1991-09-24";
        Assert.assertTrue(validatorService.isValidBirthdate(validBirthdate));
        String invalidBirthdate = LocalDate.now().minusYears(17).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Assert.assertFalse(validatorService.isValidBirthdate(invalidBirthdate));
    }
    
    @Test
    public void isValidBetAmountTest() {
        Assert.assertTrue(validatorService.isValidBetAmount("20.1"));
        Assert.assertTrue(validatorService.isValidBetAmount("20.19"));
        Assert.assertFalse(validatorService.isValidBetAmount("1111.11"));
        Assert.assertFalse(validatorService.isValidBetAmount("111.111"));
    }
    
    @Test
    public void isValidIdTest() {
        Assert.assertTrue(validatorService.isValidId("1"));
        Assert.assertFalse(validatorService.isValidId("0"));
    }
    
    @Test
    public void isNotNullTest() {
        Assert.assertTrue(validatorService.isNotNull(new Object()));
        Assert.assertFalse(validatorService.isNotNull(null));
    }
    
    @Test
    public void isValidOutcomeCoeffTest() {
        Assert.assertTrue(validatorService.isValidOutcomeCoeff("1.25"));
        Assert.assertTrue(validatorService.isValidOutcomeCoeff("99.99"));
        Assert.assertFalse(validatorService.isValidOutcomeCoeff("1"));
        Assert.assertFalse(validatorService.isValidOutcomeCoeff("100"));
    }
    
    @Test
    public void isValidBetTimeTest() {
        LocalDateTime betDT = LocalDateTime.now().minusSeconds(1);
        LocalDateTime eventDT = LocalDateTime.now();
        Assert.assertTrue(validatorService.isValidBetTime(betDT, eventDT));
        LocalDateTime evenInvalidDT = LocalDateTime.now();
        LocalDateTime betInvalidDT = LocalDateTime.now();
        Assert.assertFalse(validatorService.isValidBetTime(betInvalidDT, evenInvalidDT));
    }
    
    @Test
    public void isValidEventDateTimeTest() {
        String date = String.valueOf(LocalDateTime.now().plusDays(1));
        String dateInvalid = String.valueOf(LocalDateTime.now());
        Assert.assertTrue(validatorService.isValidEventDateTime(date));
        Assert.assertFalse(validatorService.isValidEventDateTime(dateInvalid));
    }
    
    @Test
    public void isValidEventResultTest() {
        Assert.assertTrue(validatorService.isValidEventResult("0"));
        Assert.assertTrue(validatorService.isValidEventResult("999"));
        Assert.assertFalse(validatorService.isValidEventResult("1000"));
        Assert.assertFalse(validatorService.isValidEventResult("-1"));
        Assert.assertFalse(validatorService.isValidEventResult("1.5"));
        Assert.assertFalse(validatorService.isValidEventResult("string"));
    }
    
    @Test
    public void isValidEventParticipantNameTest() {
        Assert.assertTrue(validatorService.isValidEventParticipantName("Command"));
        Assert.assertTrue(validatorService.isValidEventParticipantName("Команда"));
        Assert.assertTrue(validatorService.isValidEventParticipantName("Команда мечты."));
        Assert.assertTrue(validatorService.isValidEventParticipantName("Команда-мечты. "));
        Assert.assertTrue(validatorService.isValidEventParticipantName("654"));
        Assert.assertFalse(validatorService.isValidEventParticipantName(" Команда"));
        Assert.assertFalse(validatorService.isValidEventParticipantName("-Команда"));
    }
    
    @Test
    public void isValidRequestParamTest() {
        String valid1 = "str";
        String valid2 = "str2";
        String invalid = null;
        Assert.assertTrue(validatorService.isValidRequestParam(valid1, valid2));
        Assert.assertFalse(validatorService.isValidRequestParam(valid1, valid2, invalid));
    }
    
    @Test
    public void isValidOutcomeCoeffOnPageTest() {
        Event event = new Event();
        Outcome type1 = new Outcome();
        Outcome typeX = new Outcome();
        Outcome type2 = new Outcome();
        type1.setType(Outcome.Type.TYPE_1);
        typeX.setType(Outcome.Type.TYPE_X);
        type2.setType(Outcome.Type.TYPE_2);
        type1.setCoefficient(new BigDecimal("1.5"));
        typeX.setCoefficient(new BigDecimal("2.5"));
        type2.setCoefficient(new BigDecimal("3.5"));
        Set<Outcome> outcomeSet = new HashSet<>();
        outcomeSet.add(type1);
        outcomeSet.add(typeX);
        outcomeSet.add(type2);
        event.setOutcomeSet(outcomeSet);
    
        String outcomeCoeffOnPage = "1.5";
        String outcomeType = "1";
        Assert.assertTrue(validatorService.isValidOutcomeCoeffOnPage(outcomeCoeffOnPage, event, outcomeType));
        String outcomeCoeffOnPageInvalid = "2.5";
        Assert.assertFalse(validatorService.isValidOutcomeCoeffOnPage(outcomeCoeffOnPageInvalid, event, outcomeType));
        
    }
}

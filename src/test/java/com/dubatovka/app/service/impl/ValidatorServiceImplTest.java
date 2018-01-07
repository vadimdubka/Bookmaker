package com.dubatovka.app.service.impl;

import com.dubatovka.app.service.ServiceFactory;
import com.dubatovka.app.service.ValidatorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidatorServiceImplTest {
    ValidatorService validatorService = ServiceFactory.getInstance().getValidatorService();
    @Before
    public void setUp() {
    }
    
    @Test
    public void emailValidCheck() {
        String validEmail = "any@valid.by";
        
        Assert.assertTrue(validatorService.isValidEmail(validEmail));
    }
    
    @Test
    public void emailInvalidCheck() {
        String inValidEmail = "anyInvalid@va_12lid.bbby";
        
        Assert.assertFalse(validatorService.isValidEmail(inValidEmail));
    }
    
    @Test
    public void validateNameValidCheck() {
        String validName = "AnyName";
        
        Assert.assertTrue(validatorService.isValidName(validName));
    }
    
    @Test
    public void validateNameInvalidCheck() {
        String invalidName = "любоеИмя";
        
        Assert.assertFalse(validatorService.isValidName(invalidName));
    }
    
    @Test
    public void validateBirthdateValidCheck() {
        String validBirthdate = "1991-09-24";
        
        Assert.assertTrue(validatorService.isValidBirthdate(validBirthdate));
    }
    
    @Test
    public void validateBirthdateInvalidCheck() {
        String invalidBirthdate = LocalDate.now().minusYears(17)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        Assert.assertFalse(validatorService.isValidBirthdate(invalidBirthdate));
    }
    
    @Test
    public void validateAmountValidCheck() {
        String validAmount = "20.1";
        
        Assert.assertTrue(validatorService.isValidBetAmount(validAmount));
    }
    
    @Test
    public void validateAmountInvalidCheck() {
        Assert.assertFalse(validatorService.isValidBetAmount("1111.11"));
        Assert.assertFalse(validatorService.isValidBetAmount("111.111"));
    }
}

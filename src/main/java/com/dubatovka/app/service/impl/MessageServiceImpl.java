package com.dubatovka.app.service.impl;

import com.dubatovka.app.service.MessageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.dubatovka.app.config.ConfigConstant.ATTR_LOCALE;
import static com.dubatovka.app.config.ConfigConstant.COUNTRY_RU;
import static com.dubatovka.app.config.ConfigConstant.COUNTRY_US;
import static com.dubatovka.app.config.ConfigConstant.EMPTY_STRING;
import static com.dubatovka.app.config.ConfigConstant.EN_US;
import static com.dubatovka.app.config.ConfigConstant.LANG_EN;
import static com.dubatovka.app.config.ConfigConstant.LANG_RU;
import static com.dubatovka.app.config.ConfigConstant.MESSAGE_SEPARATOR;
import static com.dubatovka.app.config.ConfigConstant.PATH_TO_MESSAGES_BUNDLE;
import static com.dubatovka.app.config.ConfigConstant.RU_RU;

class MessageServiceImpl implements MessageService {
    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    private static final Locale LOCALE_EN_US = new Locale(LANG_EN, COUNTRY_US);
    private static final Locale LOCALE_RU_RU = new Locale(LANG_RU, COUNTRY_RU);
    
    private final ResourceBundle bundle;
    private final StringBuilder errMess = new StringBuilder();
    private final StringBuilder infMess = new StringBuilder();
    
    MessageServiceImpl() {
        bundle = ResourceBundle.getBundle(PATH_TO_MESSAGES_BUNDLE, LOCALE_RU_RU);
    }
    
    MessageServiceImpl(String localeStr) {
        Locale locale;
        switch (localeStr) {
            case EN_US:
                locale = LOCALE_EN_US;
                break;
            case RU_RU:
                locale = LOCALE_RU_RU;
                break;
            default:
                locale = LOCALE_RU_RU;
        }
        bundle = ResourceBundle.getBundle(PATH_TO_MESSAGES_BUNDLE, locale);
    }
    
    MessageServiceImpl(Locale locale, String pathToBundle) {
        bundle = ResourceBundle.getBundle(pathToBundle, locale);
    }
    
    MessageServiceImpl(HttpSession session) {
        this((String) session.getAttribute(ATTR_LOCALE));
    }
    
    @Override
    public void appendErrMessByKey(String key) {
        errMess.append(getMessageByKey(key)).append(MESSAGE_SEPARATOR);
    }
    
    @Override
    public void appendInfMessByKey(String key) {
        infMess.append(getMessageByKey(key)).append(MESSAGE_SEPARATOR);
    }
    
    @Override
    public void appendErrMess(String message) {
        errMess.append(message).append(MESSAGE_SEPARATOR);
    }
    
    @Override
    public void appendInfMess(String message) {
        infMess.append(message).append(MESSAGE_SEPARATOR);
    }
    
    @Override
    public String getErrMessContent() {
        return errMess.toString().trim();
    }
    
    @Override
    public String getInfMessContent() {
        return infMess.toString().trim();
    }
    
    @Override
    public boolean isErrMessEmpty() {
        return errMess.toString().trim().isEmpty();
    }
    
    @Override
    public boolean isInfMessEmpty() {
        return infMess.toString().trim().isEmpty();
    }
    
    @Override
    public String getMessageByKey(String key) {
        String property;
        try {
            property = bundle.getString(key);
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            property = EMPTY_STRING;
        }
        return property;
    }
    
    @Override
    public Locale getLocale() {
        return bundle.getLocale();
    }
}

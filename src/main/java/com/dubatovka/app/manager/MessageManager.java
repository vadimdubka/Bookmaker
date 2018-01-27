package com.dubatovka.app.manager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.dubatovka.app.manager.ConfigConstant.COUNTRY_RU;
import static com.dubatovka.app.manager.ConfigConstant.COUNTRY_US;
import static com.dubatovka.app.manager.ConfigConstant.EMPTY_STRING;
import static com.dubatovka.app.manager.ConfigConstant.EN_US;
import static com.dubatovka.app.manager.ConfigConstant.LANG_EN;
import static com.dubatovka.app.manager.ConfigConstant.LANG_RU;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_SEPARATOR;
import static com.dubatovka.app.manager.ConfigConstant.PATH_TO_MESSAGES_BUNDLE;
import static com.dubatovka.app.manager.ConfigConstant.RU_RU;

public final class MessageManager {
    private static final Logger logger = LogManager.getLogger(MessageManager.class);
    private static final Locale LOCALE_EN_US = new Locale(LANG_EN, COUNTRY_US);
    private static final Locale LOCALE_RU_RU = new Locale(LANG_RU, COUNTRY_RU);
    
    private final ResourceBundle bundle;
    private final StringBuilder errMess = new StringBuilder();
    private final StringBuilder infMess = new StringBuilder();
    
    private MessageManager(Locale locale) {
        bundle = ResourceBundle.getBundle(PATH_TO_MESSAGES_BUNDLE, locale);
    }
    
    public static MessageManager getMessageManager(String locale) {
        MessageManager messageManager;
        switch (locale) {
            case EN_US:
                messageManager = new MessageManager(LOCALE_EN_US);
                break;
            case RU_RU:
                messageManager = new MessageManager(LOCALE_RU_RU);
                break;
            default:
                messageManager = new MessageManager(LOCALE_RU_RU);
        }
        return messageManager;
    }
    
    public void appendErrMessByKey(String key) {
        errMess.append(getMessageByKey(key)).append(MESSAGE_SEPARATOR);
    }
    
    public void appendInfMessByKey(String key) {
        infMess.append(getMessageByKey(key)).append(MESSAGE_SEPARATOR);
    }
    
    public void appendErrMess(String message) {
        errMess.append(message).append(MESSAGE_SEPARATOR);
    }
    
    public void appendInfMess(String message) {
        infMess.append(message).append(MESSAGE_SEPARATOR);
    }
    
    public String getErrMessContent() {
        return errMess.toString().trim();
    }
    
    public String getInfMessContent() {
        return infMess.toString().trim();
    }
    
    public boolean isErrMessEmpty() {
        return errMess.toString().trim().isEmpty();
    }
    
    public boolean isInfMessEmpty() {
        return errMess.toString().trim().isEmpty();
    }
    
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
    
    public Locale getLocale() {
        return bundle.getLocale();
    }
}

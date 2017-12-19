package com.dubatovka.app.manager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.dubatovka.app.manager.ConfigConstant.*;

public final class MessageManager {
    private static final Logger logger = LogManager.getLogger(MessageManager.class);
    
    private static final Locale LOCALE_EN_US = new Locale(LANG_EN, COUNTRY_US);
    private static final Locale LOCALE_RU_RU = new Locale(LANG_RU, COUNTRY_RU);
    private static final MessageManager MESSAGE_MANAGER_US = new MessageManager(LOCALE_EN_US);
    private static final MessageManager MESSAGE_MANAGER_RU = new MessageManager(LOCALE_RU_RU);
    private static final MessageManager MESSAGE_MANAGER_DEFAULT = MESSAGE_MANAGER_RU;
    
    private final ResourceBundle bundle;
    
    private MessageManager(Locale locale) {
        bundle = ResourceBundle.getBundle(PATH_TO_MESSAGES_BUNDLE, locale);
    }
    
    public static MessageManager getMessageManager(String locale) {
        MessageManager messageManager;
        switch (locale) {
            case EN_US:
                messageManager = MESSAGE_MANAGER_US;
                break;
            case RU_RU:
                messageManager = MESSAGE_MANAGER_RU;
                break;
            default:
                messageManager = MESSAGE_MANAGER_DEFAULT;
        }
        return messageManager;
    }
    
    public String getMessage(String key) {
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

package com.dubatovka.app.service;

import java.util.Locale;

public interface MessageService {
    void appendErrMessByKey(String key);
    
    void appendInfMessByKey(String key);
    
    void appendErrMess(String message);
    
    void appendInfMess(String message);
    
    String getErrMessContent();
    
    String getInfMessContent();
    
    boolean isErrMessEmpty();
    
    boolean isInfMessEmpty();
    
    String getMessageByKey(String key);
    
    Locale getLocale();
}

package com.dubatovka.app.service;

import org.apache.commons.codec.digest.DigestUtils;

public final class EncryptorService {
    
    private EncryptorService() {
    }
    
    public static String encryptMD5(String source) {
        String result = null;
        if (source != null) {
            result = DigestUtils.md5Hex(source);
        }
        return result;
    }
}
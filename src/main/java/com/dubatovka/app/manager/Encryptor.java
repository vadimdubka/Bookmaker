package com.dubatovka.app.manager;

import org.apache.commons.codec.digest.DigestUtils;

public final class Encryptor {
    
    private Encryptor() {
    }
    
    public static String encryptMD5(String source) {
        String result = null;
        if (source != null) {
            result = DigestUtils.md5Hex(source);
        }
        return result;
    }
}
package com.denbondd.restaurant.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    /**
     * Hash array of chars using SHA-512
     *
     * @param arr what to encode
     * @return hashed string
     */
    public static String hash(char[] arr) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(new String(arr).getBytes(StandardCharsets.UTF_8));
            byte[] hash = md.digest();
            StringBuilder answ = new StringBuilder();
            for (byte b : hash) {
                answ.append(String.format("%02X", b));
            }
            return answ.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

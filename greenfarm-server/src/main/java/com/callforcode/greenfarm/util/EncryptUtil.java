package com.callforcode.greenfarm.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class EncryptUtil {

    // optional value AES/DES/DESede
    private static final String CIPHER_ALGORITHM = "AES";

    private static final String KEY = "Call for Code-Green Farm";

    @SneakyThrows
    public static String encrypt(String data) {
        SecureRandom sr = new SecureRandom();
        Key secureKey = getKey(KEY);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
        byte[] bt = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(bt);
    }

    public static String decrypt(String message) throws Exception {
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key secureKey = getKey(KEY);
        cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
        byte[] res = Base64.getDecoder().decode(message);
        res = cipher.doFinal(res);
        return new String(res);
    }

    @SneakyThrows
    private static Key getKey(String strKey) {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(strKey.getBytes());
        generator.init(128, secureRandom);
        return generator.generateKey();
    }

}

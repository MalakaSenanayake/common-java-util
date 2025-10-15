package org.m.java.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String AES_ALGORITHM = "AES";
    static final String GEN_KEY = "Ohy77rrraXaWM4nNxt/fjSYdufCzjwyHOcFDcqPpQlo=";


    // Generate a random AES key (Only run once and store the key securely)---------------------------------------------
    public static String generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256); // AES-256
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Encrypt a password-----------------------------------------------------------------------------------------------
    public static String encryptPassword(String password, String secretKey) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(secretKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt a password-----------------------------------------------------------------------------------------------
    public static String decryptPassword(String encryptedPassword, String secretKey) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(secretKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    // Encrypt a password-----------------------------------------------------------------------------------------------
    public static String encryptPassword(String password) throws Exception {
        return encryptPassword(password, GEN_KEY);
    }

    // Decrypt a password-----------------------------------------------------------------------------------------------
    public static String decryptPassword(String encryptedPassword) throws Exception {
        return decryptPassword(encryptedPassword, GEN_KEY);
    }

    //------------------------------------------------------------------------------------------------------------------
}


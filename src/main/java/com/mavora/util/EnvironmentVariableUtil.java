package com.mavora.util;

public class EnvironmentVariableUtil {
    //------------------------------------------------------------------------------------------------------------------
    public static String getSecretKey() {
        String secretKey = System.getenv().getOrDefault("SECRET_KEY","9822");
        if (secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("SECRET_KEY environment variable is not set!");
        }
        return secretKey;
    }

    //------------------------------------------------------------------------------------------------------------------
}

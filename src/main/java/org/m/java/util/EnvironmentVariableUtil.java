package org.m.java.util;

public class EnvironmentVariableUtil {
    //------------------------------------------------------------------------------------------------------------------
    public static String getSecretKey() {
        String secretKey = System.getenv("SECRET_KEY");
        if (secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("SECRET_KEY environment variable is not set!");
        }
        return secretKey;
    }

    //------------------------------------------------------------------------------------------------------------------
}

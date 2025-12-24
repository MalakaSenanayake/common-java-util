/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavora.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class for generating unique identifiers and keys.
 *
 * @author Malaka Senanayake
 */
public class GenerateUtil {

    /**
     * Generates a primary key by combining timestamp and random numbers.
     *
     * @return A unique string identifier combining timestamp and random values
     */
    public static String getPrimaryKey() {
        try {
            return getTimeStamp() + getRandomKey() + getRandomKey();
        } catch (Exception ex) {
            handleException("Failed to generate primary key", ex);
            // Fallback to UUID if timestamp/random fails
            return getUUID();
        }
    }

    /**
     * Generates a random 5-digit number as a string.
     *
     * @return A random 5-digit number as a string
     */
    private static String getRandomKey() {
        try {
            Random r = new Random();
            int low = 10000;
            int high = 99999;
            int randomValue = r.nextInt(high - low) + low;
            return String.valueOf(randomValue);
        } catch (IllegalArgumentException ex) {
            handleException("Invalid range in random key generation", ex);
            return "00000"; // Fallback value
        } catch (Exception ex) {
            handleException("Failed to generate random key", ex);
            return "00000"; // Fallback value
        }
    }

    /**
     * Generates a timestamp string in the format yyyMMddHHmmssSSS.
     *
     * @return A formatted timestamp string
     */
    private static String getTimeStamp() {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS")
                    .format(Calendar.getInstance().getTime());
            return timeStamp.replace("-", "");
        } catch (IllegalArgumentException ex) {
            handleException("Invalid date format pattern", ex);
            return "19700101000000000"; // Fallback to epoch time
        } catch (Exception ex) {
            handleException("Failed to generate timestamp", ex);
            return "19700101000000000"; // Fallback to epoch time
        }
    }

    /**
     * Generates a UUID string without hyphens.
     *
     * @return A UUID string without hyphens
     */
    private static String getUUID() {
        try {
            return UUID.randomUUID().toString().replace("-", "");
        } catch (Exception ex) {
            handleException("Failed to generate UUID", ex);
            return "defaultuuid0000000000000000000000000000"; // Fallback UUID-like string
        }
    }

    /**
     * Handles exceptions by logging them to error output with detailed information.
     *
     * @param message Context message about the error
     * @param ex Exception to handle
     */
    private static void handleException(String message, Exception ex) {
        System.err.println(message + ": " + ex.getMessage());
        ex.printStackTrace();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavora.util;

import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class for generating unique identifiers and keys.
 *
 * @author Malaka Senanayake
 */
public final class GenerateUtil {

    private static final TsidFactory FACTORY;

    static {
        // NODE_ID → environment variable එකෙන් inject කරන්න
        // Docker/JAR run කරද්දී: -DNODE_ID=1 හෝ ENV NODE_ID=1
        int nodeId = Integer.parseInt(
                System.getProperty("NODE_ID",
                        System.getenv().getOrDefault("NODE_ID", "1"))
        );

        if (nodeId < 0 || nodeId > 255) {
            throw new IllegalStateException(
                    "NODE_ID must be 0–255 for TSID-256. Got: " + nodeId
            );
        }

        // TSID-256 → 8 bits node + 14 bits sequence
        // Per node: 16,384 IDs per millisecond — ඔබට more than enough
        FACTORY = TsidFactory.builder()
                .withNodeBits(8)
                .withNode(nodeId)
                .build();
    }
    private GenerateUtil() {}

    /** Primary key generate  — For BIGINT column  */
    public static Long getPrimaryKey() {
        return FACTORY.create().toLong();
    }
    /** For Debug/logging — don't expose production API   */
    public static Tsid nextTsid() {
        return FACTORY.create();
    }

    public static String getCode(String prefix) {
        return getRandomKey(prefix);
    }
    public static String getCode() {
        return getRandomKey("");
    }

    private static String getRandomKey(String prefix) {
        try {
            Random r = new Random();
            int low = 10000;
            int high = 99999;
            int randomValue = r.nextInt(high - low) + low;
            return String.valueOf(prefix+randomValue);
        } catch (IllegalArgumentException ex) {
            handleException("Invalid range in random key generation", ex);
            return "00000"; // Fallback value
        } catch (Exception ex) {
            handleException("Failed to generate random key", ex);
            return "00000"; // Fallback value
        }
    }
    private static void handleException(String message, Exception ex) {
        System.err.println(message + ": " + ex.getMessage());
        ex.printStackTrace();
    }

}

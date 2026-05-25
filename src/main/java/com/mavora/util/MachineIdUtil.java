package com.mavora.util;

import java.net.InetAddress;

public final class MachineIdUtil {

    private static final int MAX_LENGTH = 45;

    private MachineIdUtil() {
    }

    public static String getMachineId() {
        try {
            String host = InetAddress.getLocalHost().getHostName();
            if (host == null || host.isEmpty()) {
                return "unknown";
            }
            return host.length() > MAX_LENGTH ? host.substring(0, MAX_LENGTH) : host;
        } catch (Exception e) {
            return "unknown";
        }
    }
}

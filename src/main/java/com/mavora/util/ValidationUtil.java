package com.mavora.util;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author malaka senanayake
 */

public class ValidationUtil {
    public static final int MIN_PASSWORD_LENGTH = 6;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[+]?[0-9]{9,20}$");

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !isBlank(email) && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidMobile(String mobile) {
        return !isBlank(mobile) && MOBILE_PATTERN.matcher(mobile.trim()).matches();
    }

    public static boolean minLength(String value, int min) {
        return value != null && value.length() >= min;
    }

    //-old one-----------------------------------------------------------------------------------------
    public static boolean isPositiveValue(int value) {
        return value > 0;
    }

    public static boolean isNotEmpty(String[] ar) {
        if (ar == null || ar.length == 0) {
            return false;
        }
        for (String s : ar) {
            if (s == null || s.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9]{10}$");
    }

    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }
        String validEmailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(validEmailPattern);
    }
    public static boolean isPositiveDouble(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        try {
            double value = Double.parseDouble(number);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String number) {
        if (number == null || number.trim().isEmpty() || number.startsWith("0")) {
            return false;
        }
        try {
            int value = Integer.parseInt(number);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isZeroOrDouble(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        try {
            double value = Double.parseDouble(number);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isZeroOrInteger(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        try {
            int value = Integer.parseInt(number);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean haveBalance(double balance) {
        return balance == 0;
    }

    public static boolean isEqualOrLessThan(double balance, double enteredValue) {
        return balance >= enteredValue;
    }
    public static boolean isSuccess(int primaryKey) {
        return primaryKey > 0;
    }
    public static String toPhoneNumberSMSFormat(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "";
        }
        return phoneNumber.replaceFirst("^0+(?!$)", "94");
    }
    public static <T> boolean isNotEmpty(T object) {
        if (object == null) {
            return false;
        }

        try {
            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(object);

                // Check for null or empty String
                if (value == null) {
                    return false;
                }

                if (value instanceof String && ((String) value).trim().isEmpty()) {
                    return false;
                }

                // Check for empty collections
                if (value instanceof java.util.Collection<?> && ((java.util.Collection<?>) value).isEmpty()) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("[Error] Error accessing fields of object", e);
        }

        return true;
    }


    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    //------------------------------------------------------------------------------------------------------------------
}

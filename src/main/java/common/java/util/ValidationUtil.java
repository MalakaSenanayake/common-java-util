package common.java.util;

import java.lang.reflect.Field;

/**
 * @author malaka senanayake
 */

public class ValidationUtil {
    //------------------------------------------------------------------------------------------------------------------
    public static boolean isPositiveValue(int value) {
        return value > 0;
    }

    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9]{10}$");
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }
        String validEmailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(validEmailPattern);
    }

    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
    public static boolean haveBalance(double balance) {
        return balance == 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean isEqualOrLessThan(double balance, double enteredValue) {
        return balance >= enteredValue;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean isSuccess(int primaryKey) {
        return primaryKey > 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String toPhoneNumberSMSFormat(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "";
        }
        return phoneNumber.replaceFirst("^0+(?!$)", "94");
    }

    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    //------------------------------------------------------------------------------------------------------------------
}

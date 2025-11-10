package common.java.util;

/**
 * Utility class for string manipulation operations.
 *
 * @author Malaka Senanayake
 */
public class StringUtil {

    /**
     * Removes dash-like characters (-, _, =) from the input string.
     *
     * @param value Input string to process
     * @return Processed string with dashes removed, or empty string if input is null
     */
    public static String removeDash(String value) {
        try {
            if (value == null) {
                return "";
            }
            String regx = "[-_=]";
            return value.replaceAll(regx, "");
        } catch (Exception ex) {
            handleException("Error removing dashes from string: " + value, ex);
            return value != null ? value : "";
        }
    }

    /**
     * Removes various unwanted symbols from the input string and replaces single quotes with spaces.
     *
     * @param value Input string to process
     * @return Processed string with symbols removed, or empty string if input is null
     */
    public static String removeUnwantedSymbols(String value) {
        try {
            if (value == null) {
                return "";
            }
            String regx = "[.,@#$%^&*:-=_!/\"\"]";
            return value.replaceAll(regx, "").replace("'", " ");
        } catch (Exception ex) {
            handleException("Error removing unwanted symbols from string: " + value, ex);
            return value != null ? value : "";
        }
    }

    /**
     * Removes double quotes from the input string.
     *
     * @param value Input string to process
     * @return Processed string without double quotes, or empty string if input is null
     */
    public static String removeDoubleQuotes(String value) {
        try {
            if (value == null) {
                return "";
            }
            String regx = "[\"\"]";
            return value.replaceAll(regx, "");
        } catch (Exception ex) {
            handleException("Error removing double quotes from string: " + value, ex);
            return value != null ? value : "";
        }
    }

    /**
     * Converts an array of strings into a single string with values separated by forward slashes.
     *
     * @param value Array of strings to process
     * @return Formatted string, or empty string if input is null or empty
     */
    public static String toHistoryDataFormat(String[] value) {
        try {
            if (value == null || value.length == 0) {
                return "";
            }
            StringBuilder stringValues = new StringBuilder();
            for (String s : value) {
                stringValues.append("/").append(s != null ? s : "");
            }
            return stringValues.toString().replaceFirst("/", "");
        } catch (Exception ex) {
            handleException("Error converting array to history data format", ex);
            return "";
        }
    }

    /**
     * Capitalizes the first letter of the input string and converts the rest to lowercase,
     * also normalizing multiple spaces to single spaces.
     *
     * @param value Input string to process
     * @return Formatted string, or empty string if input is null
     * @throws IllegalArgumentException if string is empty after trimming
     */
    public static String toFirstLetterCapital(String value) {
        try {
            if (value == null) {
                return "";
            }
            String trimmed = value.trim();
            if (trimmed.isEmpty()) {
                return "";
            }
            value = value.toLowerCase().replaceAll(" +", " ");
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        } catch (StringIndexOutOfBoundsException ex) {
            handleException("Error processing string to first letter capital: " + value, ex);
            return value != null ? value : "";
        } catch (Exception ex) {
            handleException("Unexpected error in toFirstLetterCapital: " + value, ex);
            return value != null ? value : "";
        }
    }

    /**
     * Formats a string into a name format by removing symbols, replacing single quotes with spaces,
     * and converting to uppercase.
     *
     * @param value Input string to process
     * @return Formatted string in uppercase, or empty string if input is null
     */
    public static String toNameFormat(String value) {
        try {
            if (value == null) {
                return "";
            }
            String regx = "[.,@#$%^&*:-=_!/\"\"]";
            return value.replaceAll(regx, "").replace("'", " ").toUpperCase();
        } catch (Exception ex) {
            handleException("Error converting string to name format: " + value, ex);
            return value != null ? value : "";
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
package common.java.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for date and time manipulation and formatting.
 *
 * @author Malaka Senanayake
 */
public class DateTimeUtil {
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATE = "1899-01-01";

    /**
     * Formats a Date object into a timestamp string.
     *
     * @param d Date to format
     * @return Formatted timestamp string, or "Invalid Date" if input is null
     */
    public static String toDateFormat(Date d) {
        try {
            if (d == null) {
                return "Invalid Date";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
            return sdf.format(d);
        } catch (Exception ex) {
            handleException("Failed to format date: " + d, ex);
            return "Invalid Date";
        }
    }

    /**
     * Converts a timestamp string to a Date object.
     *
     * @param date String in TIMESTAMP_FORMAT to parse
     * @return Parsed Date object, or null if input is invalid
     */
    public static Date toDateObject(String date) {
        try {
            if (date == null || date.trim().isEmpty()) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
            sdf.setLenient(false);
            return sdf.parse(date);
        } catch (ParseException ex) {
            handleException("Invalid date format: " + date, ex);
            return null;
        } catch (Exception ex) {
            handleException("Unexpected error parsing date: " + date, ex);
            return null;
        }
    }

    /**
     * Calculates the number of days from the input date to the current date.
     *
     * @param inputDate Date string in DATE_FORMAT
     * @return Number of days difference (negative if input is in future), or 0 if invalid
     */
    public static int daysUpToNow(String inputDate) {
        try {
            if (inputDate == null || inputDate.trim().isEmpty()) {
                return 0;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Date givenDate = sdf.parse(inputDate);
            Date today = new Date();
            long diffInMillis = today.getTime() - givenDate.getTime(); // Fixed calculation order
            return (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        } catch (ParseException ex) {
            handleException("Invalid date format for days calculation: " + inputDate, ex);
            return 0;
        } catch (Exception ex) {
            handleException("Unexpected error in days calculation: " + inputDate, ex);
            return 0;
        }
    }

    /**
     * Gets the current date as a formatted string.
     *
     * @return Current date in DATE_FORMAT, or DEFAULT_DATE if formatting fails
     */
    public static String getCurrentDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(new Date());
        } catch (Exception ex) {
            handleException("Failed to get current date", ex);
            return DEFAULT_DATE;
        }
    }

    /**
     * Gets the current time as a formatted string.
     *
     * @return Current time in TIME_FORMAT, or "00:00:00" if formatting fails
     */
    public static String getCurrentTime() {
        try {
            SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
            return df.format(new Date());
        } catch (Exception ex) {
            handleException("Failed to get current time", ex);
            return "00:00:00";
        }
    }

    /**
     * Gets the current timestamp as a formatted string.
     *
     * @return Current timestamp in TIMESTAMP_FORMAT, or default timestamp if fails
     */
    public static String getCurrentTimeStamp() {
        try {
            return new SimpleDateFormat(TIMESTAMP_FORMAT)
                    .format(new Timestamp(System.currentTimeMillis()));
        } catch (Exception ex) {
            handleException("Failed to get current timestamp", ex);
            return DEFAULT_DATE + " 00:00:00";
        }
    }

    /**
     * Formats an input date string from various possible formats to standard DATE_FORMAT.
     *
     * @param inputDate Date string in any supported format
     * @return Formatted date in DATE_FORMAT, or DEFAULT_DATE if parsing fails
     */
    public static String formatDate(String inputDate) {
        try {
            if (inputDate == null || inputDate.trim().isEmpty()) {
                return DEFAULT_DATE;
            }
            String[] possibleFormats = {
                    "yyyy-MM-dd", "dd-MM-yyyy", "MM/dd/yyyy", "yyyy/MM/dd", "dd/MM/yyyy",
                    "dd MMM yyyy", "MMM dd, yyyy", "yyyyMMdd"
            };
            for (String format : possibleFormats) {
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                    inputFormat.setLenient(false);
                    Date date = inputFormat.parse(inputDate);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT);
                    return outputFormat.format(date);
                } catch (ParseException ignored) {
                    // Try next format
                }
            }
            handleException("No valid format found for date: " + inputDate, null);
            return DEFAULT_DATE;
        } catch (Exception ex) {
            handleException("Unexpected error formatting date: " + inputDate, ex);
            return DEFAULT_DATE;
        }
    }

    /**
     * Handles exceptions by logging them to error output with detailed information.
     *
     * @param message Context message about the error
     * @param ex Exception to handle, may be null
     */
    private static void handleException(String message, Exception ex) {
        System.err.println(message + (ex != null ? ": " + ex.getMessage() : ""));
        if (ex != null) {
            ex.printStackTrace();
        }
    }
}
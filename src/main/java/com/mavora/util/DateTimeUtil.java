package com.mavora.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for date and time manipulation and formatting.
 * Legacy {@link java.util.Date} methods are unchanged for compatibility.
 * Prefer the {@link java.time} helpers for new code.
 *
 * @author Malaka Senanayake
 */
public class DateTimeUtil {

    public static final DateTimeFormatter LOCAL_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter LOCAL_DATE_TIME_MINUTES = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter LOCAL_DATE_TIME_SECONDS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATE = "1899-01-01";

    private DateTimeUtil() {
    }

    public static String formatLocalDate(LocalDate date) {
        return date == null ? null : LOCAL_DATE.format(date);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : LOCAL_DATE_TIME_MINUTES.format(dateTime);
    }

    public static String formatLocalDate(LocalDate date, DateTimeFormatter formatter) {
        if (date == null || formatter == null) {
            return null;
        }
        return formatter.format(date);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null || formatter == null) {
            return null;
        }
        return formatter.format(dateTime);
    }

    public static String formatLocalDateOrDash(LocalDate date) {
        return date == null ? "-" : LOCAL_DATE.format(date);
    }

    public static String formatLocalDateTimeOrDash(LocalDateTime dateTime) {
        return dateTime == null ? "-" : LOCAL_DATE_TIME_MINUTES.format(dateTime);
    }

    public static LocalDate today() {
        return today(Clock.systemDefaultZone());
    }

    public static LocalDate today(Clock clock) {
        return LocalDate.now(clock);
    }

    public static int daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    public static int daysBetweenClampedAtZero(LocalDate start, LocalDate end) {
        return Math.max(0, daysBetween(start, end));
    }

    public static LocalDate todayMinusMonths(int months) {
        return todayMinusMonths(months, Clock.systemDefaultZone());
    }

    public static LocalDate todayMinusMonths(int months, Clock clock) {
        return today(clock).minusMonths(months);
    }

    public static LocalDate todayMinusDays(int days) {
        return todayMinusDays(days, Clock.systemDefaultZone());
    }

    public static LocalDate todayMinusDays(int days, Clock clock) {
        return today(clock).minusDays(days);
    }

    public static DateRangeValidationResult validateDateRange(LocalDate fromDate, LocalDate toDate) {
        return validateRequiredAndOrder(fromDate, toDate);
    }

    public static DateRangeValidationResult validateDateRangeDays(LocalDate fromDate, LocalDate toDate, int maxDays) {
        DateRangeValidationResult base = validateRequiredAndOrder(fromDate, toDate);
        if (!base.isValid()) {
            return base;
        }
        if (ChronoUnit.DAYS.between(fromDate, toDate) > maxDays) {
            return DateRangeValidationResult.invalid(DateRangeFailureKind.SPAN_EXCEEDED);
        }
        return DateRangeValidationResult.valid();
    }

    public static DateRangeValidationResult validateDateRangeMonths(LocalDate fromDate, LocalDate toDate,
                                                                    int maxMonths) {
        DateRangeValidationResult base = validateRequiredAndOrder(fromDate, toDate);
        if (!base.isValid()) {
            return base;
        }
        if (toDate.isAfter(fromDate.plusMonths(maxMonths))) {
            return DateRangeValidationResult.invalid(DateRangeFailureKind.SPAN_EXCEEDED);
        }
        return DateRangeValidationResult.valid();
    }

    public static LocalDate parseLocalDate(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return LocalDate.parse(trimmed, LOCAL_DATE);
    }

    public static LocalDate toLocalDate(Date sqlDate) {
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }

    public static Date toSqlDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    /**
     * Formats a Date object into a timestamp string.
     *
     * @param d Date to format
     * @return Formatted timestamp string, or "Invalid Date" if input is null
     */
    public static String toDateFormat(java.util.Date d) {
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
    public static java.util.Date toDateObject(String date) {
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
     * @return Number of days difference, or 0 if invalid
     */
    public static int daysUpToNow(String inputDate) {
        try {
            if (inputDate == null || inputDate.trim().isEmpty()) {
                return 0;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            java.util.Date givenDate = sdf.parse(inputDate);
            java.util.Date today = new java.util.Date();
            long diffInMillis = today.getTime() - givenDate.getTime();
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
            return sdf.format(new java.util.Date());
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
            return df.format(new java.util.Date());
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
                    DATE_FORMAT, "dd-MM-yyyy", "MM/dd/yyyy", "yyyy/MM/dd", "dd/MM/yyyy",
                    "dd MMM yyyy", "MMM dd, yyyy", "yyyyMMdd"
            };
            for (String format : possibleFormats) {
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                    inputFormat.setLenient(false);
                    java.util.Date parsed = inputFormat.parse(inputDate);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT);
                    return outputFormat.format(parsed);
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

    private static DateRangeValidationResult validateRequiredAndOrder(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            return DateRangeValidationResult.invalid(DateRangeFailureKind.DATES_REQUIRED);
        }
        if (fromDate.isAfter(toDate)) {
            return DateRangeValidationResult.invalid(DateRangeFailureKind.FROM_AFTER_TO);
        }
        return DateRangeValidationResult.valid();
    }

    private static void handleException(String message, Exception ex) {
        System.err.println(message + (ex != null ? ": " + ex.getMessage() : ""));
        if (ex != null) {
            ex.printStackTrace();
        }
    }
}

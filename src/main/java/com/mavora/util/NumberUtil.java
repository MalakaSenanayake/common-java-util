package com.mavora.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;

/**
 * Numeric helpers for PayTrack and other Mavora apps.
 * Money operations use {@link BigDecimal} internally to avoid float drift.
 */
public final class NumberUtil {

    public static final int MONEY_SCALE = 2;
    public static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private static final DecimalFormat CURRENCY_FORMAT = createCurrencyFormat();
    private static final DecimalFormat PLAIN_MONEY_FORMAT = createPlainMoneyFormat();

    private NumberUtil() {
    }

    // ── Money — core ────────────────────────────────────────────────────────

    public static double roundMoney(double value) {
        return roundMoney(toBigDecimal(value));
    }

    public static Double roundMoney(Double value) {
        return value == null ? null : roundMoney(value.doubleValue());
    }

    public static Double parseMoney(String value) {
        if (isBlank(value)) {
            return null;
        }
        try {
            return roundMoney(new BigDecimal(value.trim()));
        } catch (NumberFormatException | ArithmeticException e) {
            return null;
        }
    }

    public static double parseMoney(String value, double defaultValue) {
        Double parsed = parseMoney(value);
        return parsed == null ? defaultValue : parsed;
    }

    public static double sumMoney(double... amounts) {
        BigDecimal total = BigDecimal.ZERO;
        for (double amount : amounts) {
            total = total.add(toBigDecimal(amount));
        }
        return roundMoney(total);
    }

    public static double sumMoney(Collection<Double> amounts) {
        BigDecimal total = BigDecimal.ZERO;
        if (amounts != null) {
            for (Double amount : amounts) {
                if (amount != null) {
                    total = total.add(toBigDecimal(amount));
                }
            }
        }
        return roundMoney(total);
    }

    public static boolean moneyEquals(double left, double right) {
        return toBigDecimal(left)
                .setScale(MONEY_SCALE, MONEY_ROUNDING)
                .compareTo(toBigDecimal(right).setScale(MONEY_SCALE, MONEY_ROUNDING)) == 0;
    }

    public static double round(double value, int scale) {
        return toBigDecimal(value).setScale(scale, MONEY_ROUNDING).doubleValue();
    }

    // ── Money — display ─────────────────────────────────────────────────────

    public static String formatCurrency(double value) {
        return CURRENCY_FORMAT.format(roundMoney(value));
    }

    public static String formatCurrency(Double value) {
        return value == null ? "" : formatCurrency(value.doubleValue());
    }

    public static String formatCurrency(String value) {
        Double parsed = parseMoney(value);
        return parsed == null ? "" : formatCurrency(parsed);
    }

    public static String formatPlainMoney(double value) {
        return PLAIN_MONEY_FORMAT.format(roundMoney(value));
    }

    public static String formatPositiveMoney(double value) {
        return formatPlainMoney(Math.abs(value));
    }

    // ── General numeric ─────────────────────────────────────────────────────

    public static int toInteger(String value) {
        if (isBlank(value)) {
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    public static double toDouble(String value) {
        if (isBlank(value)) {
            return 0.0;
        }
        return Double.parseDouble(value.trim());
    }

    // ── Backward compatibility ──────────────────────────────────────────────

    /** @deprecated Use {@link #roundMoney(double)} */
    @Deprecated
    public static double toDecimalFormat(double value) {
        return roundMoney(value);
    }

    /** @deprecated Use {@link #formatPlainMoney(double)} via {@link #parseMoney(String)} */
    @Deprecated
    public static String toDecimalFormat(String value) {
        Double parsed = parseMoney(value);
        return parsed == null ? "" : formatPlainMoney(parsed);
    }

    /** @deprecated Use {@link #formatCurrency(double)} */
    @Deprecated
    public static String toCurrencyFormat(double value) {
        return formatCurrency(value);
    }

    /** @deprecated Use {@link #formatCurrency(String)} */
    @Deprecated
    public static String toCurrencyFormat(String value) {
        return formatCurrency(value);
    }

    /** @deprecated Use {@link #formatPlainMoney(double)} */
    @Deprecated
    public static String toDoubleFormat(double value) {
        return formatPlainMoney(value);
    }

    /** @deprecated Use {@link #formatPlainMoney(double)} */
    @Deprecated
    public static String toDouble(double value) {
        return formatPlainMoney(value);
    }

    /** @deprecated Use {@link #formatPositiveMoney(double)} */
    @Deprecated
    public static String toPositiveDouble(double value) {
        return formatPositiveMoney(value);
    }

    // ── Internal ────────────────────────────────────────────────────────────

    private static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    /** Single BigDecimal rounding path used by parseMoney, sumMoney, roundMoney. */
    private static double roundMoney(BigDecimal value) {
        return value.setScale(MONEY_SCALE, MONEY_ROUNDING).doubleValue();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static DecimalFormat createCurrencyFormat() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#,##0.00", symbols);
        format.setRoundingMode(MONEY_ROUNDING);
        return format;
    }

    private static DecimalFormat createPlainMoneyFormat() {
        DecimalFormat format = new DecimalFormat("0.00");
        format.setRoundingMode(MONEY_ROUNDING);
        return format;
    }
}

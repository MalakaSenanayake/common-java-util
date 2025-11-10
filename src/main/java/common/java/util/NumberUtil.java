/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.java.util;


import java.text.DecimalFormat;

/**
 * @author malaka senanayake
 */
public class NumberUtil {
    //------------------------------------------------------------------------------------------------------------------

    public static String toCurrencyFormat(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0.00"; // Default value for invalid input
        }
        try {
            String nonCommaValue = value.replace(",", "").replace(" ", "");
            double finalOutput = Double.parseDouble(nonCommaValue);
            DecimalFormat decFor = new DecimalFormat("#,###,##0.00");
            return decFor.format(finalOutput);
        } catch (NumberFormatException e) {
            return "Invalid Input"; // Handle non-numeric input safely
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    public static String toDecimalFormat(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0.00";
        }
        try {
            String nonCommaValue = value.replace(",", "").replace(" ", "");
            double finalOutput = Double.parseDouble(nonCommaValue);
            DecimalFormat decFor = new DecimalFormat("0.00");
            return decFor.format(finalOutput);
        } catch (NumberFormatException e) {
            return "Invalid Input";
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    public static double toDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0; // Default safe value
        }
        try {
            String nonCommaValue = value.replace(",", "").replace(" ", "");
            return Double.parseDouble(nonCommaValue);
        } catch (NumberFormatException e) {
            return 0.0; // Handle invalid input safely
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    public static String toDouble(double value) {
        DecimalFormat decFor = new DecimalFormat("0.00");
        return decFor.format(value);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static double toDoubleFormat(double value) {
        DecimalFormat decFor = new DecimalFormat("0.00");
        try {
            return Double.parseDouble(decFor.format(value));
        } catch (NumberFormatException e) {
            return 0.0; // Handle potential parsing issues
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String toPositiveDouble(double value) {
        DecimalFormat decFor = new DecimalFormat("0.00");
        return decFor.format(Math.abs(value));
    }

    //------------------------------------------------------------------------------------------------------------------
    public static double toDecimalFormat(double value) {
        DecimalFormat decFor = new DecimalFormat("0.00");
        try {
            return Double.parseDouble(decFor.format(value));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    public static String toCurrencyFormat(double value) {
        DecimalFormat decFor = new DecimalFormat("#,###,###.00");
        return decFor.format(value);
    }
    //------------------------------------------------------------------------------------------------------------------

    public static int toInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0; // Default value for invalid input
        }
        try {
            String nonCommaValue = value.replace(",", "").replace(" ", "");
            return Integer.parseInt(nonCommaValue);
        } catch (NumberFormatException e) {
            return 0; // Handle invalid input safely
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}

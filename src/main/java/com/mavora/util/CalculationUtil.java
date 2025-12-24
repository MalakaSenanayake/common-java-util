package com.mavora.util;


/**
 * @author malaka senanayake
 */
public class CalculationUtil {
    //------------------------------------------------------------------------------------------------------------------
    public static double addTwoValues(String valueOne, String valueTwo) {
        double calculatedValue = 0;
        calculatedValue = (NumberUtil.toDouble(valueOne)) + (NumberUtil.toDouble(valueTwo));
        return calculatedValue;
    }
    //------------------------------------------------------------------------------------------------------------------
}

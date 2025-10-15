package org.m.java.util;

/**
 * @author malaka senanayake
 */
public class BooleanUtil {
    //------------------------------------------------------------------------------------------------------------------

    public static boolean isHasPermission(String permission) {
        boolean b = false;
        if (permission.equals("0")) {
            b = false;
        } else if (permission.equals("1")) {
            b = true;
        }
        return b;
    }
    //------------------------------------------------------------------------------------------------------------------
}

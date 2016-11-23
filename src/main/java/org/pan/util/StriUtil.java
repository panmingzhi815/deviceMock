package org.pan.util;

/**
 * Created by panmingzhi on 2016/11/22 0022.
 */
public class StriUtil {
    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

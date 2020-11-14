package com.eghm.websocket.utils;

/**
 * @author 殿小二
 * @date 2020/11/14
 */
public class StringUtil {

    public static final String ENCRYPT = "VqvN2GFjPlRKuQp5B4YLScWk6z09nw8sa1O3Z7htxymfgorAMDIbEXidTJeUCH";

    /**
     * 数字进制 用于基础加密
     * @param value value
     * @return 可解密
     */
    public static String encryptNumber(long value) {
        StringBuilder builder = new StringBuilder();
        int length = ENCRYPT.length();
        while (value > 0) {
            builder.append(ENCRYPT.charAt((int)(value % length)));
            value /= length;
        }
        return builder.toString();
    }

    /**
     * 数字进制 用于基础解密
     * @param value value
     * @return 可解密
     */
    public static long decryptNumber(String value) {
        int scale = ENCRYPT.length();
        int length = value.length();
        long result = 0L;
        for (int i = length - 1; i >=0 ; i--) {
            int index = ENCRYPT.indexOf(value.charAt(i));
            result = result * scale + index;
        }
        return result;
    }
}

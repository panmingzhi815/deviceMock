package org.pan.util;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by panmingzhi on 2016/11/22 0022.
 */
public class ByteUtil {
    public static byte toCheckByte(byte[] bytes, int start, int end) {
        throw new NotImplementedException();
    }

    /**
     * 将地址转化为字符串表示
     * @param bytes
     * @return
     */
    public static String toAddressStr(byte[] bytes) {
        int areaBit = bytes[0] * 32 + bytes[1];
        int deviceBit = bytes[2] * 32 + bytes[3];
        return areaBit + "." + deviceBit;
    }
}

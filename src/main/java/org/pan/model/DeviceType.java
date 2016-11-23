package org.pan.model;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public enum  DeviceType {
    门禁,DLP2000停车场,DLP4000停车场;

    public static String[] toStringArray(){
        DeviceType[] values = DeviceType.values();
        String[] strings = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strings[i] = values[i].name();
        }
        return strings;
    }
}

package org.pan.model;

import java.util.Random;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public enum  EventType {
    随机,正常刷卡,非法刷卡,内读头正常刷卡,内读头非法刷卡,远程开门,远程关门,消防输入;

    public static String[] toStringArray(){
        EventType[] values = EventType.values();
        String[] strings = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strings[i] = values[i].name();
        }
        return strings;
    }

    public static EventType parse(String eventType) {
        EventType[] values = EventType.values();
        for (EventType value : values) {
            if (value.name().equals(eventType)) {
                return value;
            }
        }
        return EventType.随机;
    }

    public EventType getEventType() {
        if (this == EventType.随机) {
            Random random = new Random();
            EventType[] values = EventType.values();
            int i = random.nextInt(values.length-1);
             return values[i+1];
        }
        return this;
    }
}

package org.pan.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public class DeviceRecord {
    private SimpleStringProperty card = new SimpleStringProperty();
    private SimpleStringProperty evenType = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty device = new SimpleStringProperty();

    public DeviceRecord() {
    }

    public DeviceRecord(String card, String evenType, String time,String device) {
        this.card.set(card);
        this.evenType.set(evenType);
        this.time.set(time);
        this.device.set(device);
    }

    public String getCard() {
        return card.get();
    }

    public SimpleStringProperty cardProperty() {
        return card;
    }

    public void setCard(String card) {
        this.card.set(card);
    }

    public String getEvenType() {
        return evenType.get();
    }

    public SimpleStringProperty evenTypeProperty() {
        return evenType;
    }

    public void setEvenType(String evenType) {
        this.evenType.set(evenType);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getDevice() {
        return device.get();
    }

    public SimpleStringProperty deviceProperty() {
        return device;
    }

    public void setDevice(String device) {
        this.device.set(device);
    }

    @Override
    public String toString() {
        return "DeviceRecord{" +
                "card=" + card +
                ", evenType=" + evenType +
                ", time=" + time +
                ", device=" + device +
                '}';
    }
}

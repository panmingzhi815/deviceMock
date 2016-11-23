package org.pan.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public class Device {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty deviceType = new SimpleStringProperty();

    public Device(){}

    public void copyFromDevice(Optional<Device> device){
        this.name.setValue(device.getName());
        this.address.setValue(device.getAddress());
        this.deviceType.setValue(device.getDeviceType());
    }

    public Function<? super Optional<Device>, ? extends String> getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getDeviceType() {
        return deviceType.get();
    }

    public StringProperty deviceTypeProperty() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType.set(deviceType);
    }
}

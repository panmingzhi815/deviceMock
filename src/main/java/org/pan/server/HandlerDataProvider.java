package org.pan.server;

import com.google.inject.Singleton;
import org.pan.model.Device;
import org.pan.model.DeviceRecord;

import java.util.Optional;

/**
 * Created by panmingzhi on 2016/11/22 0022.
 */
@Singleton
public interface HandlerDataProvider {

    Optional<Device> getDevice(String address);

    Optional<DeviceRecord> getFirstAndRemoveRecord(Device device);
}

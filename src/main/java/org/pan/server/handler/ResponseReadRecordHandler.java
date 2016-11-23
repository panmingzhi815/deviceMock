package org.pan.server.handler;

import org.pan.model.Device;
import org.pan.model.DeviceRecord;
import org.pan.server.HandlerDataProvider;
import org.pan.util.ByteUtil;

import java.util.Arrays;
import java.util.Optional;

/**
 * 响应读记录消息
 * Created by panmingzhi on 2016/11/22 0022.
 */
public class ResponseReadRecordHandler implements MessageHandler {

    public final int LENGTH = 20;

    private HandlerDataProvider handlerDataProvider;

    public ResponseReadRecordHandler(HandlerDataProvider handlerDataProvider) {
        this.handlerDataProvider = handlerDataProvider;
    }

    @Override
    public Optional<byte[]> response(byte[] bytes) {
        String address = ByteUtil.toAddressStr(Arrays.copyOfRange(bytes,2,6));
        Optional<Device> deviceOptional = handlerDataProvider.getDevice(address);
        if (!deviceOptional.isPresent()) {
            return Optional.empty();
        }
        Device device = deviceOptional.get();
        Optional<DeviceRecord> firstAndRemoveRecord = handlerDataProvider.getFirstAndRemoveRecord(device);
        if (firstAndRemoveRecord.isPresent()) {
            return Optional.empty();
        }

        byte[] result = new byte[LENGTH];
        return Optional.of(result);
    }
}

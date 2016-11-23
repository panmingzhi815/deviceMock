package org.pan.server;

import com.google.inject.Singleton;
import org.pan.server.handler.MessageHandler;
import org.pan.server.handler.ResponseDownloadPrivilegeHandler;
import org.pan.server.handler.ResponseReadRecordHandler;

import java.util.Optional;

/**
 * Created by panmingzhi on 2016/11/22 0022.
 */
@Singleton
public class HandlerSwitcherRegister implements MessageHandler {

    private final HandlerDataProvider handlerDataProvider;

    public HandlerSwitcherRegister(HandlerDataProvider handlerDataProvider) {
        this.handlerDataProvider = handlerDataProvider;
    }

    @Override
    public Optional<byte[]> response(byte[] bytes) {
        if (bytes.length == 5){
            ResponseReadRecordHandler responseReadRecordHandler = new ResponseReadRecordHandler(this.handlerDataProvider);
            return responseReadRecordHandler.response(bytes);
        }else if(bytes.length == 5){
            ResponseDownloadPrivilegeHandler responseDownloadPrivilegeHandler = new ResponseDownloadPrivilegeHandler();
            return responseDownloadPrivilegeHandler.response(bytes);
        }
        return null;
    }

}

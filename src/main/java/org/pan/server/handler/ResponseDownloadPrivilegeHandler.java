package org.pan.server.handler;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

/**
 * 响应下载权限
 * Created by panmingzhi on 2016/11/22 0022.
 */
public class ResponseDownloadPrivilegeHandler implements MessageHandler {
    @Override
    public Optional<byte[]> response(byte[] bytes) {
        throw new NotImplementedException();
    }
}

package org.pan.server.handler;

import com.google.inject.Singleton;

import java.util.Optional;

/**
 * 消息处理句柄
 * Created by panmingzhi on 2016/11/22 0022.
 */
@Singleton
public interface MessageHandler {

    Optional<byte[]> response(byte[] bytes);

}

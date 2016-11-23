package org.pan.server;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pan.server.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 响应动作
 * Created by panmingzhi on 2016/11/20 0020.
 */
@Singleton
public class ResponseHandler extends IoHandlerAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);
    private final MessageHandler handlerSwitcherList;

    @Inject
    public ResponseHandler(MessageHandler handlerSwitcher) {
        this.handlerSwitcherList = handlerSwitcher;
    }

    @Override
    public void sessionOpened(IoSession session) {
        String remoteAddress = session.getRemoteAddress().toString();
        LOGGER.debug("打开会话：{}",remoteAddress);
    }

    @Override
    public void sessionClosed(IoSession session) {
        String remoteAddress = session.getRemoteAddress().toString();
        LOGGER.debug("关闭会话：{}",remoteAddress);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        String remoteAddress = session.getRemoteAddress().toString();
        LOGGER.error("会话：{} 发生错误",remoteAddress,cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        String remoteAddress = session.getRemoteAddress().toString();
        if (message instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) message;
            byteBuffer.flip();
            byte[] array = byteBuffer.array();
            LOGGER.debug("收到：{} 消息：{}",remoteAddress, ByteArrayUtil.toHexString(array));
            byte[] response = handlerSwitcherList.response(array);
            if (response != null) {
                session.write(response);
            }
        }
    }

}

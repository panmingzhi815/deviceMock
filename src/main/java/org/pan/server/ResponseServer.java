package org.pan.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.pan.server.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 响应服务器
 * Created by panmingzhi on 2016/11/20 0020.
 */
@Singleton
public class ResponseServer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ResponseServer.class);
    public static final int PORT = 10099;

    private final ResponseHandler responseHandler;
    private final MessageHandler handlerSwitcher;
    private final ByteArrayCodecFactory byteArrayCodecFactory;
    private NioSocketAcceptor nioSocketAcceptor;

    @Inject
    public ResponseServer(ResponseHandler responseHandler, MessageHandler handlerSwitcher, ByteArrayCodecFactory byteArrayCodecFactory) {
        this.responseHandler = responseHandler;
        this.handlerSwitcher = handlerSwitcher;
        this.byteArrayCodecFactory = byteArrayCodecFactory;
    }

    public void start(){
        if (nioSocketAcceptor == null){
            nioSocketAcceptor = new NioSocketAcceptor();
            nioSocketAcceptor.getFilterChain().addLast("logger",new LoggingFilter());
            nioSocketAcceptor.getFilterChain().addLast("coder",new ProtocolCodecFilter(byteArrayCodecFactory));
            nioSocketAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,5000);
            nioSocketAcceptor.setHandler(new ResponseHandler(handlerSwitcher));
        }
        try {
            nioSocketAcceptor.bind(new InetSocketAddress(PORT));
            LOGGER.error("启动端口{}服务成功",PORT);
        } catch (Exception e) {
            LOGGER.error("启动端口{}服务失败",PORT,e);
        }
    }

    public void stop(){
        try {
            nioSocketAcceptor.unbind();
            LOGGER.error("解绑端口{}服务成功",PORT);
        } catch (Exception e) {
            LOGGER.error("解绑端口{}服务失败",PORT,e);
        }
    }

    public boolean isDisposed(){
        return nioSocketAcceptor == null || !nioSocketAcceptor.isActive();
    }

}

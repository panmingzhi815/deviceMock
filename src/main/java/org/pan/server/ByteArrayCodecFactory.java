package org.pan.server;

import com.google.inject.Singleton;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.*;

/**
 * Created by panmingzhi on 2016/11/22 0022.
 */
@Singleton
public class ByteArrayCodecFactory implements ProtocolCodecFactory {

    private ByteArrayDecoder decoder;
    private ByteArrayEncoder encoder;

    public ByteArrayCodecFactory() {
        encoder = new ByteArrayEncoder();
        decoder = new ByteArrayDecoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }


}

class ByteArrayDecoder extends ProtocolDecoderAdapter {

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int limit = in.limit();
        byte[] bytes = new byte[limit];
        in.get(bytes);
        out.write(bytes);
    }

}

class ByteArrayEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        byte[] bytes = (byte[]) message;
        IoBuffer buffer = IoBuffer.allocate(256);
        buffer.setAutoExpand(true);
        buffer.put(bytes);
        buffer.flip();
        out.write(buffer);
        out.flush();
        buffer.free();
    }
}

package netty_beginner.TestDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength){
        if(frameLength<=0){
            throw new IllegalArgumentException("frame must be positive: "+frameLength);
        }
        this.frameLength=frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        while (in.readableBytes()>=frameLength){
            System.out.println("len= "+in.readableBytes());
            ByteBuf buf = in.readBytes(frameLength);//每次只读取3个字节
            out.add(buf);//入站数据的读取在out中
        }
    }
}

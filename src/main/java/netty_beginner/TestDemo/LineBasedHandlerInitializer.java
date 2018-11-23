package netty_beginner.TestDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.Charset;

public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch)throws Exception{
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024,2,8));
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
            ByteBuf in=(ByteBuf)msg;
            System.out.println("msg= "+in.toString(Charset.forName("utf-8")));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx){
            //将消息冲刷到客户端，并且关闭channel
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (5)
//        super.exceptionCaught(ctx, cause);
            cause.printStackTrace();
            ctx.close();
        }
    }
}

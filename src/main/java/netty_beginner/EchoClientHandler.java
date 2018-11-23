package netty_beginner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {//对进入的数据进行加工

    @Override//因为NettyNioServer中没有对bytebuf的handler（即，channelRead），因此这里客户端不能发送bytebuf，不然会出现NIO错误
    public void channelActive(ChannelHandlerContext ctx){
        //这里不能用write，如果用write，那么数据始终不会发送到远程服务端，因此连接成功后就暂停在这里了
        System.out.println("connection success!!!");
        //如何server也有一个writeAndFlush,就不能使用下面的这个。。。暂时不清楚为什么
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hard to forgot u!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
       System.out.println("client received: "+ in.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer("hard to!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
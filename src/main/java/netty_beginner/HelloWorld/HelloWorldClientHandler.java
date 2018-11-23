package netty_beginner.HelloWorld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;

import static netty_beginner.HelloWorld.AttributeMapConstant.NETTY_CHANNEL_KEY;

public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //问题是，如何对channel刚进来就赋予初始值呢
        Attribute<NettyChannel> attr1 = ctx.attr(NETTY_CHANNEL_KEY);//实质是获取channel的属性，当新的类被设置属性后，整个ctx的channel的属性都是它
        NettyChannel nChannel = attr1.get();
        if (nChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            attr1.setIfAbsent(newNChannel);
            nChannel = attr1.get();
        } else {
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
        }
        System.out.println("nChannel= "+nChannel);
        System.out.println("HelloWorldC0ientHandler Active");
        ctx.fireChannelActive();
    }
//当所有的handler都channelActive后，再channelRead
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Attribute<NettyChannel> attr = ctx.attr(NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();
        if (nChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            nChannel = attr.setIfAbsent(newNChannel);
        } else {
            System.out.println("channelRead attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "=======000000" + nChannel.getCreateDate());
        }

        System.out.println("HelloWorld000000lientHandler read Message:" + msg);

        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}


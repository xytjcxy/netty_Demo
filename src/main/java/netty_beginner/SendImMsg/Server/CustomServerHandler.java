package netty_beginner.SendImMsg.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty_beginner.SendImMsg.CustomMsg;

public class CustomServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object m) throws Exception {
        CustomMsg msg=(CustomMsg)m;
        String fromUid = msg.getFromUid();
        String toUid=msg.getToUid();
        int length=msg.getLength();
        String body=msg.getBody();
        System.out.println("Server->Client:"+fromUid+" "+toUid+" "+length+" "+body);
        body+="cxy send de!!!";
        ctx.channel().writeAndFlush(new CustomMsg(fromUid,toUid,body.length(),body));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}


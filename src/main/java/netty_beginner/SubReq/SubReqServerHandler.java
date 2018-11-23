package netty_beginner.SubReq;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("--------------------------------handler channelActive------------");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception{

        SubscribeResp resp = new SubscribeResp();
        resp.setnSubReqID(555);
        resp.setRespCode(0);
        resp.setDesc("-------Netty book order succeed, 3days later, sent to the designated address");
        ctx.writeAndFlush(resp);	// 反馈消息

        SubscribeReq req = (SubscribeReq)msg;   // 订购内容
        if("XXYY".equalsIgnoreCase(req.getUserName())){
            System.out.println("接收到的数据: [  " + req.toString() + "   ]");
        }

    }

    @Override
    public  void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("---------------exceptionCaught 网络异常，关闭网络");
        cause.printStackTrace();
        ctx.close();
    }
}

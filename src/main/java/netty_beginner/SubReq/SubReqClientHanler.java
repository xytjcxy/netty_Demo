package netty_beginner.SubReq;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqClientHanler extends ChannelInboundHandlerAdapter {

    public SubReqClientHanler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("----------------handler channelActive-----准备发送十个数据-------");

        for(int i = 0; i<10; i++){
//			ctx.write(subReq(i));
            SubscribeReq req = new SubscribeReq();
            req.setAddress("深圳蛇口");
            req.setPhoneNumber("13888886666");
            req.setProductName("Netty Book");
            req.setSubReqID(i);
            req.setUserName("XXYY");
            ctx.write(req);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception{
        System.out.println("--------channelRead---服务器发来的数据为：[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception{
        System.out.println("----------------handler channelReadComplete");
        ctx.flush();
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("--------------------------------------------------------------------------handler exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

}


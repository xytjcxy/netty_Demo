package netty_beginner.SendImMsg.Client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import netty_beginner.SendImMsg.CustomMsg;

import java.nio.charset.Charset;

import static netty_beginner.SendImMsg.Server.CustomServer.LENGTH_FIELD_LENGTH;
import static netty_beginner.SendImMsg.Server.CustomServer.LENGTH_FIELD_OFFSET;

public class CustomerDecoder extends LengthFieldBasedFrameDecoder {
    CustomerDecoder(int maxlength, int offset, int length) {
        super(maxlength, offset, length);
    }
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in)throws Exception{
        if(in.readableBytes()<LENGTH_FIELD_LENGTH+LENGTH_FIELD_OFFSET)throw new Exception("消息错误");//应该尝试当消息发送不正确时，向客户端再次请求数据
        String fromUid=in.readBytes(LENGTH_FIELD_OFFSET>>1).toString(Charset.forName("utf-8"));
        String toUid=in.readBytes(LENGTH_FIELD_OFFSET>>1).toString(Charset.forName("utf-8"));
        int length=in.readInt();//获取消息体长度
        ByteBuf buf=in.readBytes(length);//读取消息体
        byte[]b=new byte[buf.readableBytes()];
        buf.readBytes(b);

        String msgBody=new String(b,"utf-8");
        CustomMsg msg=new CustomMsg(fromUid,toUid,length,msgBody);
        System.out.println("client decode msg"+msg.getFromUid()+" "+msg.getToUid()+" "+msg.getLength()+" "+msgBody);
        return msg;
    }
}

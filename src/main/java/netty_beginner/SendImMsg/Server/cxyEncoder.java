package netty_beginner.SendImMsg.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty_beginner.SendImMsg.CustomMsg;

import java.nio.charset.Charset;

public class cxyEncoder extends MessageToByteEncoder<CustomMsg> {
    @Override
    public void encode(ChannelHandlerContext ctx, CustomMsg msg, ByteBuf out)throws Exception{
        if(msg==null)throw new Exception("msg is null");
        String fromUid=msg.getFromUid();
        String toUid=msg.getToUid();
        int length=msg.getLength();
        out.writeBytes(fromUid.getBytes(Charset.forName("utf-8")));//写消息的来源
        out.writeBytes(toUid.getBytes(Charset.forName("utf-8")));//写消息的目的地
        out.writeInt(length);
        out.writeBytes(msg.getBody().getBytes(Charset.forName("utf-8")));
        System.out.println("server encode"+msg.getFromUid()+" "+msg.getToUid()+" "+msg.getLength()+" "+msg.getBody());
    }
}

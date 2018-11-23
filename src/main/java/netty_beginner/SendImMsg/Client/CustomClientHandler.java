package netty_beginner.SendImMsg.Client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import netty_beginner.SendImMsg.CustomMsg;

import java.lang.ref.Reference;
import java.nio.charset.Charset;
import java.util.Scanner;

public class CustomClientHandler extends ChannelInboundHandlerAdapter{

    public byte[] intToBytes(int value) {

        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public byte[] unitByteArray(byte[] byte1, byte[] byte2) {
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext,Object m)throws Exception{
        CustomMsg msg=(CustomMsg)m;
        String fromUid = msg.getFromUid();
        String toUid=msg.getToUid();
        int length=msg.getLength();
        String body=msg.getBody();
        System.out.println("clientHandler has recived msg:"+fromUid+" "+toUid+" "+length+" "+body);
        Scanner sc=new Scanner(System.in);
        System.out.println("please cin your toUid");
        String toUid1=sc.nextLine();
        System.out.println("please cin your words:");
        String body1=sc.nextLine();
        int length1=body1.length();
        channelHandlerContext.channel().writeAndFlush(new CustomMsg(fromUid,toUid1,length1,body1));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (5)
        cause.printStackTrace();
        ctx.close();
    }
}


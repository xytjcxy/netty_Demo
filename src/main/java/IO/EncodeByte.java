package IO;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class EncodeByte {
    public static void main(String[] args) {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("So hard to forget you!!!", utf8);
        ByteBuf temp = Unpooled.buffer(24);
        buf.readByte();
        System.out.println(buf.readableBytes());
        buf.readBytes(temp,buf.readableBytes());
//        write
//        while (temp.isReadable())
//        System.out.println((char) temp.readByte());
//        System.out.println(temp.writableBytes());
//        System.out.println(buf.readableBytes());
//        ByteBuf it = Unpooled.copiedBuffer(buf.readBytes(),utf8);
//        System.out.println(temp.readableBytes());
//        每读一个char,readindex向前进1
//        System.out.println((char) sliced.readByte());
//        System.out.println((char) sliced.readByte());
//        buf.setByte(3,(byte)'J');
//        System.out.println((char) sliced.getByte(3));

    }

}

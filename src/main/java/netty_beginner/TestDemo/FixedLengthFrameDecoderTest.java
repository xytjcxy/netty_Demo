package netty_beginner.TestDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded(){
        ByteBuf buf = Unpooled.buffer();
        for (int i=50;i<59;i++){
            buf.writeByte(i);
        }
        //需要注意的是，duplicate产生的复制并不会增加buf的引用，因此，当input传给别的使用的时候，必须要用retain，否则buf的计数在writeInbound后变为0
        ByteBuf input=buf.duplicate();
        //往channel中加handler,从而实现handler方法的测试
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //input被解码，引用减一
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());
        //read msg,每次读，都是读取out中的第一项（3字节）
        ByteBuf read=(ByteBuf) channel.readInbound();
        //注意，readSlice是一个会前进的指针，readInbound也会前进
//        System.out.println("buf= "+buf.readSlice(6).toString(Charset.forName("utf-8"))+" read= "+((ByteBuf)channel.readInbound()).toString(Charset.forName("utf-8")));
//        System.out.println("buf= "+buf.readSlice(3).toString(Charset.forName("utf-8"))+"read= "+((ByteBuf)channel.readInbound()).toString(Charset.forName("utf-8")));
        assertEquals(buf.readSlice(3),read);
        read.release();

        read=(ByteBuf)channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read=(ByteBuf)channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();

    }
}

package netty_beginner.TestDemo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

public class TestDeeemo {
    @Test
    public void ttte(){
        String a="hello, world!";
        ByteBuf res= Unpooled.buffer();
        res.writeBytes(a.getBytes(Charset.forName("utf-8")));
//        ByteBuf res=a.getBytes(Charset.forName("utf-8"));
        EmbeddedChannel channel=new EmbeddedChannel(new LineBasedHandlerInitializer());
        channel.writeInbound(res);
    }
}

package netty_beginner.TestDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded(){
        ByteBuf buf= Unpooled.buffer();
        for (int i=1;i<10;i++)buf.writeInt(i*-1);
        EmbeddedChannel channel=new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));//此时buf的引用计数=0
        assertTrue(channel.finish());

        for(int i=1;i<10;i++){
            assertEquals(i,channel.readOutbound());//readOutbound每读一次，指针前进一格
        }
        assertNull(channel.readOutbound());
    }
}

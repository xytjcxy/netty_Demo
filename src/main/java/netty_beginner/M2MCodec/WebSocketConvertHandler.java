package netty_beginner.M2MCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.List;

//<>中的第一个参数是代表入站接收数据类型，第二个参数代表入站应用程序处理数据类型
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame,
        WebSocketConvertHandler.MyWebSocketFrame> {
    //在编码成了自定义的数据之后，再转化为bytebuf进行传输使用
    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketConvertHandler.MyWebSocketFrame msg
    , List<Object> out)throws Exception{
        ByteBuf paload=msg.getData().duplicate().retain();
        switch (msg.getType()){
            case BINARY:
                out.add(new BinaryWebSocketFrame(paload));//将Bytebuf编码成想要的
                break;
            default:
                throw new IllegalStateException("eee");
        }
    }
    //解码成自定义类型
    @Override
    protected void decode(ChannelHandlerContext ctx,WebSocketFrame msg,List<Object>out){
        ByteBuf pyload = msg.content().duplicate().retain();
        //根据消息类型，自定义新的类型数据,对数据进行处理
        if(msg instanceof BinaryWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.BINARY,pyload));
        }
    }

    public static final class MyWebSocketFrame{
        public enum FrameType{
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION
        }
        private final FrameType type;
        private final ByteBuf data;

        public MyWebSocketFrame(FrameType type,ByteBuf data){
            this.type=type;
            this.data=data;
        }
        public FrameType getType(){
            return type;
        }
        public ByteBuf getData(){
            return data;
        }
    }
}

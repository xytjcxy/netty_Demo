package netty_beginner.SendImMsg.Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty_beginner.SendImMsg.CustomMsg;

import static netty_beginner.SendImMsg.Server.CustomServer.LENGTH_FIELD_LENGTH;
import static netty_beginner.SendImMsg.Server.CustomServer.LENGTH_FIELD_OFFSET;
import static netty_beginner.SendImMsg.Server.CustomServer.MAX_FRAME_LENGTH;

public class CustomClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new CustomerDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH));
                            ch.pipeline().addLast(new CustomerEncoder());
                            ch.pipeline().addLast(new CustomClientHandler());//根据返回类型，判断是否要终止ctx
                        }
                    }).option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = b.connect(HOST, PORT).sync();
            if(future.isSuccess())System.out.println("client connect success!");
            String fromUid="1234567890987654321";
            String toUid="0000000000000000000";
            CustomMsg customMsg=new CustomMsg(fromUid,toUid,0,"");
            future.channel().writeAndFlush(customMsg);//client连接server之后的第一步就是发送uid
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}


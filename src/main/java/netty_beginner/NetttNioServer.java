package netty_beginner;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NetttNioServer {

    private final int port;

    public NetttNioServer(int port) {
        this.port = port;
    }
    public void server()throws Exception{
        final ByteBuf buf=Unpooled.copiedBuffer("hello world!\r\n", Charset.forName("UTF-8"));
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)throws Exception{
                            ch.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter(){
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx)throws Exception{
                                            ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                        }
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx,Object msg) {
                                            ByteBuf in=(ByteBuf)msg;
                                            System.out.println("Server received: "+in.toString(CharsetUtil.UTF_8));
                                            ctx.write(msg);
                                        }

                                    });
                        }
                    });
            ChannelFuture f=b.bind().sync();
            System.out.println(NetttNioServer.class.getName()+"started and xy listen on"+f.channel().localAddress());
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
    public static void main(String[] args) throws Exception {
        int port=8080;
        new NetttNioServer(port).server();
    }
}

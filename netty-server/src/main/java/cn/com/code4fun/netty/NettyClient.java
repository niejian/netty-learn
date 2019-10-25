package cn.com.code4fun.netty;

import cn.com.code4fun.netty.encode.DelimiterBasedFrameEncoder;
import cn.com.code4fun.netty.handler.ChatClientInitializer;
import cn.com.code4fun.netty.handler.ClientHandler;
import cn.com.code4fun.netty.instance.NettyConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @desc: cn.com.code4fun.netty.NettyClient
 * @author: niejian9001@163.com
 * @date: 2019-10-23 10:49
 */
public class NettyClient {
    public static synchronized void start(Integer port) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
//                    .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
    //                channel.pipeline().addLast(new StringEncoder());
                    // 对服务端返回的消息通过_$进行分隔，并且每次查找的最大大小为1024字节
                    channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                            Unpooled.wrappedBuffer(NettyConst.delimiter.getBytes())));
                    // 对客户端发送的数据进行编码。
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new DelimiterBasedFrameEncoder(NettyConst.delimiter));
                    // 客户端发送消息给服务端，并处理服务端响应的消息
                    channel.pipeline().addLast(new ClientHandler());
                }
            });

//        Channel channel = bootstrap.connect("localhost", port).channel();
            ChannelFuture future = bootstrap.connect("localhost", port).sync();

            future.addListener(cha -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功");
                }
            });
            future.channel().closeFuture().sync();
            /**
             *  如果客户端发送比较长的数据，服务端接收的数据可能会出现粘包的现象。
             *  为处理这种情况，需要客户端、服务端就得做好拆包的处理
             */
//        channel.writeAndFlush("1234567890abcdefghijklmnopqrstuvwxyz");

            int i = 0;
//            while (1 > 2) {
//                i++;
//                channel.writeAndFlush("1234567890abcdefghijklmnopqrstuvwxyz");

//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        NettyClient.start(9001);
    }

}

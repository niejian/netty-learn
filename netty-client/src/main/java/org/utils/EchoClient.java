package org.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.utils.handler.EchoClientHandler;

import java.net.InetSocketAddress;

/**
 * @desc: org.utils.EchoClient
 * @author: niejian9001@gmail.com
 * @date: 2019-09-29 13:52
 */
public class EchoClient {
    private String host;
    private Integer port;

    public EchoClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        // EventLoop用户处理Channel的io操作，一个单一的EventLoop通常灰处理多个Channel事件。
        // 一个EventLoopGroup可以包含一个或者多个的EventLoop和提供一种
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //
                        // ChannelPipeline 提供了一个容器给 ChannelHandler 链并提供了一个API
                        // 用于管理沿着链入站和出站事件的流动
                        socketChannel.pipeline().addLast(new EchoClientHandler());
                    }
                });
        // netty所有操作都是异步的，一个操作可能无法立即返回，需要另外一种方法再之后来确定这个异步方法的结果。
        // 要想拿到处理后的结果，netty提供接口ChannelFuture，它的addli
        ChannelFuture channelFuture = bootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws Exception {
        EchoClient echoClient = new EchoClient("localhost", 10086);
        echoClient.start();
    }

}

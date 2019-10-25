package cn.com.code4fun.netty;

import cn.com.code4fun.netty.handler.ChatServerInitializer;
import cn.com.code4fun.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc: cn.com.code4fun.netty.ChatNettyServer
 * @author: niejian9001@163.com
 * @date: 2019-10-25 15:18
 */
@Slf4j
public class ChatNettyServer {
    private int port;

    public ChatNettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ChatNettyServer(9002).run();

    }
    public void run() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            log.info("server 启动");
            // 绑定端口，开始接受连接进来的连接
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

            log.info("server 关闭");


        }
    }

}

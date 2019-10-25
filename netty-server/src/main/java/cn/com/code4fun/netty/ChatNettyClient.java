package cn.com.code4fun.netty;

import cn.com.code4fun.netty.handler.ChatClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @desc: cn.com.code4fun.netty.ChatNettyClient
 * @author: niejian9001@163.com
 * @date: 2019-10-25 15:37
 */
public class ChatNettyClient {
    private String host;
    private int port;

    public ChatNettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new ChatNettyClient("127.0.0.1", 9002).run();
    }

    public void run() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            Channel channel = bootstrap.connect(this.host, this.port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();

        }
    }
}

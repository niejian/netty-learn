package cn.com.code4fun.netty;

import cn.com.code4fun.netty.encode.DelimiterBasedFrameEncoder;
import cn.com.code4fun.netty.handler.ServerHandler;
import cn.com.code4fun.netty.instance.NettyConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * netty 服务端
 * @desc: cn.com.code4fun.netty.NettyServer
 * @author: niejian9001@163.com
 * @date: 2019-10-23 09:54
 */
public class NettyServer {




    /**
     *  Server 启动
     *
      */
    public static synchronized void start(Integer port) {

        ServerBootstrap serverBootstrap = null;
        // server 启动监听线程
        NioEventLoopGroup boos = new NioEventLoopGroup();
        // 客户端连接server的监听绑定线程
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            serverBootstrap = new ServerBootstrap();


            //
            serverBootstrap.group(boos, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
//                            // 处理拆包、粘包的问题
                            ByteBuf byteBuf = Unpooled.copiedBuffer(NettyConst.delimiter.getBytes());
                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                            channel.pipeline().addLast(new StringDecoder());
//                            channel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                                @Override
//                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//                                    System.out.println(s);
//                                }
//                            });
                            // 自定义的解码器，主要作用是在返回的响应数据添加分隔符
                            channel.pipeline().addLast(new DelimiterBasedFrameEncoder(NettyConst.delimiter));
                            // 最终数据组处理，可以给客户端回复消息
                            channel.pipeline().addLast(new ServerHandler());
//                            channel.pipeline().addLast()

                        }
                    });

            // 绑定监听端口，调用sync()同步阻塞方法等待绑定操作完
            ChannelFuture future = serverBootstrap.bind(port).sync();
            if (future.isSuccess()) {
                System.out.println("server started!");
            }

            // 成功绑定端口后，给channel增加一个管道关闭的监听同步器并同步阻塞，
            // 直到channel关闭，线程才会往下执行，结束进程
            future.channel().closeFuture().sync();


        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        NettyServer.start(9001);
    }
}

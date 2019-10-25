package cn.com.code4fun.netty.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * 最终的数据处理
 * @desc: cn.com.code4fun.netty.handler.ServerHandler
 * @author: niejian9001@163.com
 * @date: 2019-10-23 11:40
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        //log.info("服务端收到消息：{} ", msg);

//        // 回复客户端，如果有必要的话
//        channelHandlerContext.writeAndFlush("hello client");

        Channel incoming = channelHandlerContext.channel();
        log.info("服务端收到来自{}的消息：{} ", "["+ incoming.remoteAddress() +"]",msg);
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
            } else {
                channel.writeAndFlush("[you] - " + msg +"\n");
            }

        }
    }

    /**
     * 新的连接连接进来
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("chatClient: {} 上线！", incoming.remoteAddress() );
    }

    /**
     * 客户端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("chatClient: {} 下线！", incoming.remoteAddress() );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端：{} 异常", channel.remoteAddress());
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 新链接加入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel addChannel = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[server] - " + addChannel.remoteAddress() + " 加入\n");
        }
        channels.add(ctx.channel());


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel addChannel = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[server] - " + addChannel.remoteAddress() + " 离开\n");
        }
        channels.remove(ctx.channel());
    }
}


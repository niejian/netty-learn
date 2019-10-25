package cn.com.code4fun.netty.handler;

import cn.com.code4fun.netty.dto.RpcRequest;
import cn.com.code4fun.netty.dto.RpcResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @desc: cn.com.code4fun.netty.handler.RpcServerHandler
 * @author: niejian9001@163.com
 * @date: 2019-10-24 16:26
 */
@Slf4j
public class RpcServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 客户端和服务端连接成功触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("已收到消息。");
        super.channelActive(ctx);
    }

    /**
     * 接受client发送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        RpcRequest request = (RpcRequest) msg;
        log.info("接收到客户端消息：{}", msg.toString());
        // 返回的数据结构
        RpcResponse response = new RpcResponse();
        response.setId(UUID.randomUUID().toString());
        response.setData("server响应结果");
        response.setStatus(1);
        ctx.writeAndFlush(response);



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        log.info("服务端数据接收完毕");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

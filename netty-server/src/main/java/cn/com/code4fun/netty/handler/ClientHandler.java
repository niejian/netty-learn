package cn.com.code4fun.netty.handler;

import cn.com.code4fun.netty.instance.NettyConst;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @desc: cn.com.code4fun.netty.handler.ClientHandler
 * @author: niejian9001@163.com
 * @date: 2019-10-23 14:05
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NettyConst.DATE_FORMAT);

    /**
     * 接受服务端返回消息
     * @param channelHandlerContext
     * @param s 服务端返回消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //log.info(simpleDateFormat.format(new Date()) + "===> client receive msg: " + s);
        log.info(s);
    }


//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
//        Scanner scanner =  new Scanner(System.in);
//
//        log.info("请输入消息：");
//        while (scanner.hasNext()) {
//            String next = scanner.next();
//            ctx.writeAndFlush(simpleDateFormat.format(new Date()) + "-->" + next);
//        }
//
////        while (!"q".equals(next)) {
////            ctx.writeAndFlush(simpleDateFormat.format(new Date()) + "-->" +NettyConst.MSG);
////
////        }
//    }
}

package cn.com.code4fun.netty.handler;

import cn.com.code4fun.netty.encode.DelimiterBasedFrameEncoder;
import cn.com.code4fun.netty.instance.NettyConst;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc: cn.com.code4fun.netty.handler.ChatClientInitializer
 * @author: niejian9001@163.com
 * @date: 2019-10-25 15:14
 */
@Slf4j
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new DelimiterBasedFrameEncoder(NettyConst.delimiter))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringDecoder())
                .addLast("handler", new ServerHandler());

        log.info("chat client: {} 连接上了" , socketChannel.remoteAddress());
    }
}

package cn.com.code4fun.netty.handler;

import cn.com.code4fun.netty.encode.DelimiterBasedFrameEncoder;
import cn.com.code4fun.netty.handler.ClientHandler;
import cn.com.code4fun.netty.instance.NettyConst;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @desc: cn.com.code4fun.netty.handler.ChatClientInitializer
 * @author: niejian9001@163.com
 * @date: 2019-10-25 15:25
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new DelimiterBasedFrameEncoder(NettyConst.delimiter))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder())
                .addLast("handler", new ClientHandler());

    }
}

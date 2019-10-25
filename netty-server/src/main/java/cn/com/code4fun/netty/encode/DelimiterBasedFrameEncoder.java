package cn.com.code4fun.netty.encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 在返回数据之后添加分隔符
 * @desc: cn.com.code4fun.netty.encode.DelimiterBasedFrameEncoder
 * @author: niejian9001@163.com
 * @date: 2019-10-23 13:41
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {

    private String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }



    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        // 在响应数据之后添加分隔符
        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer((s + delimiter).getBytes()));
    }
}

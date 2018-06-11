package com.wpixel.utils;

import com.wpixel.mode.Image;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @author 蒂花之秀
 *
 */
public class Encoder extends MessageToByteEncoder<Image> {

	private static final int HEAD_FLAG = 0x77aa77aa; 
    private static final int TAIL_FLAG = 0x77ab77ab;
    
	@Override
	protected void encode(ChannelHandlerContext ctx, Image obj, ByteBuf buf) throws Exception {
		//包头
		buf.writeInt(HEAD_FLAG);
		//长度
		buf.writeInt(ByteObjConverter.objectToByte(obj).length);
		//内容
		buf.writeBytes(ByteObjConverter.objectToByte(obj));
		//包尾
		buf.writeInt(TAIL_FLAG);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}
	
	
}

package com.wpixel.utils;

import java.util.List;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 解码器
 * @author 蒂花之秀
 *
 */
public class Decoder extends ByteToMessageDecoder {
	private static Logger logger = Logger.getLogger(Decoder.class);  

	private static final int HEAD_FLAG = 0x77aa77aa; //4字节
    private static final int TAIL_FLAG = 0x77ab77ab;
    
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		while(true){
//			System.out.println("read:"+buf.readerIndex()+";write:"+buf.writerIndex()+";capacity:"+buf.capacity());
			buf.markReaderIndex();//标记记录点
			if(buf.readableBytes() < 8) {
				buf.resetReaderIndex();
				return;
			}
			int headFlag = buf.readInt(); //包头
			if(headFlag != HEAD_FLAG) {
				ctx.channel().close();
				buf.clear();
				return;
			}
			int bodyLen = buf.readInt(); //长度
			if(buf.readableBytes() < bodyLen) {
				buf.resetReaderIndex();
				return;
			}
			
			
			ByteBuf body = ctx.alloc().buffer(bodyLen);
			buf.readBytes(body);//内容
			byte[] bytes = new byte[body.readableBytes()];
			body.readBytes(bytes);
			Object obj = ByteObjConverter.byteToObject(bytes);
			
			
			if(buf.readableBytes() < 4) {
				buf.resetReaderIndex();
				return;
			}
			int tail = buf.readInt();//包尾
			if(tail != TAIL_FLAG) {
				ctx.close();
				buf.clear();
				return;
			};
			list.add(obj);
			buf.discardReadBytes();//将已经读取的内容删除
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		String clientIp = ctx.channel().remoteAddress().toString();
		logger.info(clientIp+"断开连接");
		ctx.channel().close();
	}
	
}

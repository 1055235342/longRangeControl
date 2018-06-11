package com.wpixel.controller;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;

import com.wpixel.mode.Image;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerRevAndSend extends SimpleChannelInboundHandler<Object> {

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("start up");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		Image image1 = (Image)msg;
		//获得一个屏幕的截图  
		while(true){
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(0, 0, (int)d.getWidth(), (int)d.getHeight()));
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", stream);
			image1.setBytes(stream.toByteArray());
			image1.setLength(stream.toByteArray().length);
			ctx.writeAndFlush(image1);
			Thread.sleep(100);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}

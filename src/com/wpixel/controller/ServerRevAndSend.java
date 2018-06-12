package com.wpixel.controller;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.wpixel.mode.Image;

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
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			BufferedImage cursor= ImageIO.read(new File("src/com/wpixel/images/icon_cursor.png"));//cursor.gif 是你的鼠标图片  
			Point p = MouseInfo.getPointerInfo().getLocation(); 
			image.createGraphics().drawImage(cursor, p.x, p.y, null);  
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", stream);
			image1.setBytes(stream.toByteArray());
			image1.setLength(stream.toByteArray().length);
			ctx.writeAndFlush(image1);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}

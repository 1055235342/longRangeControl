package com.wpixel.controller;

import com.wpixel.mode.Image;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientRevAndSend extends SimpleChannelInboundHandler {

	public static Image image = null;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		image = (Image)msg;
	}
}

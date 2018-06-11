package com.wpixel.main;

import com.wpixel.controller.ClientRevAndSend;
import com.wpixel.mode.Image;
import com.wpixel.utils.Decoder;
import com.wpixel.utils.Encoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Client {

	public static void start() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//创建辅助工具类
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);      
			b.channel(NioSocketChannel.class);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast(new ReadTimeoutHandler(2)); 
					sc.pipeline().addLast(new Encoder());
					sc.pipeline().addLast(new Decoder());
					sc.pipeline().addLast(new ClientRevAndSend());
				}
			});
			
			ChannelFuture cf = b.connect("localhost", 5555).sync();
			
			Image image = new Image();
			image.setBytes("start".getBytes());
			image.setLength(image.getBytes().length);
			cf.channel().writeAndFlush(image);
			cf.channel().closeFuture().sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}

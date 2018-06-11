package com.wpixel.main;

import com.wpixel.controller.ServerRevAndSend;
import com.wpixel.utils.Decoder;
import com.wpixel.utils.Encoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//创建辅助工具类
            ServerBootstrap b = new ServerBootstrap();
            //指定两个线程组
            b.group(bossGroup, workerGroup);
            //指定NIO模式
            b.channel(NioServerSocketChannel.class);
            //设置tcp链接缓冲区
            b.option(ChannelOption.SO_BACKLOG, 1024);
            //设置发送缓冲大小
            b.option(ChannelOption.SO_SNDBUF, 1024);
            //设置接受缓冲大小
            b.option(ChannelOption.SO_RCVBUF, 1024);
            //保持连接
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
    			@Override
    			protected void initChannel(SocketChannel sc) throws Exception {
    				sc.pipeline().addLast(new Encoder());
					sc.pipeline().addLast(new Decoder());
    				sc.pipeline().addLast(new ServerRevAndSend());
    			}
    		});
            
            ChannelFuture cf = b.bind(5555).sync();
            cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

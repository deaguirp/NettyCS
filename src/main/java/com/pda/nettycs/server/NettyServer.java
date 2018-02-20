package com.pda.nettycs.server;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;
    
/**
 * Discards any incoming data.
 */
@Log4j2
public class NettyServer {
    
    private int port;
    
    @Inject
    @ServerHandler
    private ChannelHandler handler; //Ojo esto obliga a que los handlers sean @Sharables
    
    public NettyServer withPort(int port) {
        this.port = port;
        return this;
    }
    
    public void run() throws InterruptedException {
    	log.info("Server Iniciado");
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)

				@Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(NettyServer.this.handler);
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
    
            Executors.newSingleThreadScheduledExecutor().schedule(() -> shutDown(bossGroup, workerGroup),				
				5, TimeUnit.MINUTES);
            f.channel().closeFuture().sync();
        } finally {
            shutDown(bossGroup, workerGroup);
        }
        log.info("Server finished.");
        System.exit(0);
    }

	private void shutDown(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
		log.info("Shutting down the server...");
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}
    
}
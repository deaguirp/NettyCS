package com.pda.nettycs.client;

import com.google.inject.Inject;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AccessLevel;
import lombok.Getter;

public class NettyClient {
	
	@Inject
	@ClientHandler
	@Getter(AccessLevel.PROTECTED)
	private ChannelHandler handler;
	
	@Inject
	protected
	Target target;

	private NioEventLoopGroup workerGroup;

	public void run() throws InterruptedException{
        this.workerGroup = new NioEventLoopGroup();
        
        try {
            Bootstrap b = initBootstrap();
            
            // Start the client.
            ChannelFuture f = b.connect(target.getHost(), target.getPort()).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}

	protected Bootstrap initBootstrap() {
		Bootstrap b = new Bootstrap(); // (1)
		b.group(workerGroup); // (2)
		b.channel(NioSocketChannel.class); // (3)
		b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
		b.handler(this.handler);
		return b;
	}
	
	protected NioEventLoopGroup getWorkerGroup() {
		return workerGroup;
	}

}
